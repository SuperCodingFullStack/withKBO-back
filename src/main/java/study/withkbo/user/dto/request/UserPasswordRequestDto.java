package study.withkbo.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPasswordRequestDto {

    private String checkPassword;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()_+=<>?]).{6,20}$",
            message = "비밀번호는 최소 6자 이상 20자 이하이며 하나 이상의 숫자, 소문자 특수문자를 포함하여야 합니다."
    )
    private String newPassword;
}
