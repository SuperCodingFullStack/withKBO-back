package study.withkbo.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.team.entity.Team;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

    // ID 역할 -  이메일 - 공백 불가, 이메일 형식 확인
    @NotBlank(message = "이메일은 필수 항목 입니다.")
    @Email(message = "바르지 않은 이메일 형식 입니다.")
    private String username;

    // 비밀번호 - 공백 불가, 최소 6자, 최대 20자, 하나 이상의 숫자, 소문자, 특수문자 포함
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()_+=<>?]).{6,20}$",
            message = "비밀번호는 최소 6자 이상 20자 이하이며 하나 이상의 숫자, 소문자 특수문자를 포함하여야 합니다."
    )
    private String password;

    // 닉네임 - 공백 불가, 최소 2자, 최대 15자
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Size(min = 2, max = 15, message = "닉네임은 최소 2자 이상 최대 15자 이하여야 합니다.")
    private String nickname;

    // 전화번호 - 공백 불가, 010-0000-0000 형식만 허용
    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(
            regexp = "^010-[0-9]{4}-[0-9]{4}$",
            message = "전화번호는 010-0000-0000 형식이여야 합니다."
    )
    private String phone;

    // 주소 - 공백 불가
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    @NotBlank(message = "프로필 이미지는 필수 항목입니다.")
    private String profileImg;

    private String team;
}

