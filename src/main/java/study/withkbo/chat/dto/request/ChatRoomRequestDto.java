package study.withkbo.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRequestDto {
    private Long id;
    private String roomName;
    private LocalDateTime createdDate;
}
