package study.withkbo.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.notification.dto.response.NotificationResponseDto;
import study.withkbo.notification.dto.response.NotifyDto;
import study.withkbo.notification.entity.Notification;
import study.withkbo.notification.repository.EmitterRepository;
import study.withkbo.notification.repository.NotifyRepository;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60 * 1000L * 60;
    private final EmitterRepository emitterRepository;
    private final NotifyRepository notifyRepository;

    public SseEmitter subscribe(String username, String lastEventId, UserDetailsImpl user) {
        String emitterId = makeTimeIncludeId(username);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(username);
        sendNotification(emitter,eventId,emitterId, "EventStream Created. [userName=" + username + "]");

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().name("ping").data(""));
            } catch (IOException e) {
                log.error("Error sending ping message", e);
                emitterRepository.deleteById(emitterId);
            }
        }, 0, 30, TimeUnit.SECONDS);

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

    public void send(User sender,User receiver, Notification.NotificationType notificationType, Notification.ReadStatus readStatus,String content, String url) {
        Notification notification = notifyRepository.save(createNotification(sender,receiver, notificationType, readStatus,content, url));

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

    private Notification createNotification(User sender,User receiver, Notification.NotificationType notificationType,
                                            Notification.ReadStatus readStatus, String content, String url) {
        return Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .readStatus(readStatus)
                .build();
    }

    public List<NotificationResponseDto> selectNotification(Long id) {
        List<Notification> notificationList = notifyRepository.findByReceiverIdAndReadStatus(id, Notification.ReadStatus.UNREAD);
        return notificationList.stream().map(NotificationResponseDto::new).toList();
    }

    @Transactional
    public void notificationTypeUpdate(Long notificationId, Long id) {
        Notification notification = notifyRepository.findByIdAndReceiverId(notificationId,id)
                .orElseThrow(() -> new CommonException(CommonError.NOTIFICATION_NOT_FOUND));

        notification.setReadStatus(Notification.ReadStatus.READ);
    }
}
