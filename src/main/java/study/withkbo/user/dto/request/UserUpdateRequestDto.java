package study.withkbo.user.dto.request;

import lombok.Getter;
import study.withkbo.team.entity.Team;

@Getter
public class UserUpdateRequestDto {
    private String phone;
    private String address;
    private String profileImg;
    private String nickname;
    private Team team;

}
