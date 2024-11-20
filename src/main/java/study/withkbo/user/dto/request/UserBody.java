package study.withkbo.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBody {

    private String email;

    private String pwd;

    private String name;

    private String nickname;

    private String phone;

    private boolean phoneAuth;

    private String address;

    private String profileImg;

}
