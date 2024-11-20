package study.withkbo.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @JsonProperty("u_Email")
    private String uEmail;

    @JsonProperty("uPwd")
    private String uPwd;

    @JsonProperty("uName")
    private String uName;

    @JsonProperty("uNickname")
    private String uNickname;

    @JsonProperty("uPhone")
    private String uPhone;

    @JsonProperty("uPhoneAuth")
    private boolean uPhoneAuth;

    @JsonProperty("uAddress")
    private String uAddress;

    @JsonProperty("profileImg")
    private String profileImg;

}
