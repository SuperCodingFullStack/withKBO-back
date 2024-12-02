package study.withkbo.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.notification.dto.response.NotificationResponseDto;
import study.withkbo.notification.service.NotificationService;
import study.withkbo.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userDetails.getUser().getUsername(), lastEventId, userDetails);
    }

    @GetMapping("/notification")
    public ApiResponseDto<List<NotificationResponseDto>> selectNotification(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<NotificationResponseDto> result = notificationService.selectNotification(userDetails.getUser().getId());
        return ApiResponseDto.success(MessageType.RETRIEVE,result);
    }

    @PostMapping("/notificationType/{notificationId}")
    public ApiResponseDto<String> notificationTypeUpdate(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        notificationService.notificationTypeUpdate(notificationId,userDetails.getUser().getId());
        return ApiResponseDto.success(MessageType.UPDATE, "알림을 읽었습니다.");
    }
}
