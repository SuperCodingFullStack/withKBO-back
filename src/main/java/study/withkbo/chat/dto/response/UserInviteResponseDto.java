package study.withkbo.chat.dto.response;

import lombok.Getter;
import study.withkbo.user.entity.User;

@Getter
public class UserInviteResponseDto {
    private Long id;
    private String nickname;

    public UserInviteResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }
}
