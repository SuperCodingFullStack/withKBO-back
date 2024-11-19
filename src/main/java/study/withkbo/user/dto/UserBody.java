package study.withkbo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBody {

    private String uEmail;
    private String uPwd;
    private String uName;
    private String uNickname;
    private String uPhone;
    private boolean uPhoneAuth;
    private String uAddress;
    private String profileImg;

}
