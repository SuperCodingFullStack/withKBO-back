package study.withkbo.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import study.withkbo.notification.dto.response.NotifyDto;
import study.withkbo.notification.entity.Notification;
import study.withkbo.notification.repository.EmitterRepository;
import study.withkbo.notification.repository.NotifyRepository;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60 * 1000L * 60;
    private final EmitterRepository emitterRepository;
    private final NotifyRepository notifyRepository;
    //private final NotificationService notificationService;

    public SseEmitter subscribe(String username, String lastEventId, UserDetailsImpl user) {
        String emitterId = makeTimeIncludeId(username);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(username);
        sendNotification(emitter,eventId,emitterId, "EventStream Created. [userName=" + username + "]");

        //알림을 보낸 유저 객체 ,알림을 받을 상대방 유저 객체, 알림타입, 읽음여부, "내용", "요청 url"
        this.send(user.getUser(), Notification.NotificationType.PARTY,
                Notification.ReadStatus.UNREAD,"참가요청이 왔습니다.","/api/connect");

        if(hasLostData(lastEventId)){
            sendLostData(lastEventId, username, emitterId, emitter);
        }
        return emitter;
    }

    private void sendLostData(String lastEventId, String username, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartsWithByUserId(username);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try{
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private String makeTimeIncludeId(String username) {
        return username + "_" + System.currentTimeMillis();
    }

    public void send(User sender, Notification.NotificationType notificationType, Notification.ReadStatus readStatus,String content, String url) {
        Notification notification = notifyRepository.save(createNotification(sender, notificationType, readStatus,content, url));

        String receiverUserName = sender.getUsername();
        String eventId = receiverUserName + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverUserName);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotifyDto.Response.createResponse(notification));
                }
        );
    }

    private Notification createNotification(User sender, Notification.NotificationType notificationType,
                                            Notification.ReadStatus readStatus, String content, String url) {
        return Notification.builder()
                .sender(sender)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .readStatus(readStatus)
                .build();
    }
}
