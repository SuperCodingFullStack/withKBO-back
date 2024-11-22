package study.withkbo.user.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String username;
    private String password;
}
