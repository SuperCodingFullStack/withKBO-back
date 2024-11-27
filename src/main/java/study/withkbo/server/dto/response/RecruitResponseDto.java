package study.withkbo.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.withkbo.chat.entity.ChatMessage;
import study.withkbo.chat.entity.ChatRoom;
import study.withkbo.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitResponseDto {
    // 서버 -> 클라이언트
    private ChatMessage chatMessage;
    private User user;
}
