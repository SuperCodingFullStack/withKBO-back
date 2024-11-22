package study.withkbo.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {


    //id 역할이라고 보시면 됩니다
    private String username;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String phone;

    private String address;

    private String profileImg;


}
