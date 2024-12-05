package study.withkbo.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.notification.entity.Notification;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private String  senderName;
    private String receiverName;
    private String content;
    private Notification.NotificationType notificationType;
    private LocalDateTime createdAt;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.senderName = notification.getSender().getUsername();
        this.receiverName = notification.getReceiver().getUsername();
        this.content = notification.getContent();
        this.notificationType = notification.getNotificationType();
        this.createdAt = notification.getCreatedDate();
    }
}
