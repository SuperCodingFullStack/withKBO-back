package study.withkbo.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.notification.entity.Notification;


public class NotifyDto {
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    public static class Response {
        String id;
        String sender;
        String content;
        String readStatus;
        String createdAt;
        public static Response createResponse(Notification notify) {
            return Response.builder()
                    .content(notify.getContent())
                    .id(notify.getId().toString())
                    .sender(notify.getSender().getNickname())
                    .readStatus(String.valueOf(notify.getReadStatus()))
                    .createdAt(notify.getCreatedDate().toString())
                    .build();

        }
    }
}
