package study.withkbo.server.dto.request;

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
public class RecruitRequestDto {
    // 클라이언트 -> 서버
    private ChatMessage chatMessage;
}
