package study.withkbo.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String email;

    private String pwd;

    private String name;

    private String nickname;

    private String phone;

    private boolean phoneAuth;

    private String address;

    private String profileImg;

}
