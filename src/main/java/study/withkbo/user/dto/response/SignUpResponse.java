package study.withkbo.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import study.withkbo.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {

    private User user;
    private Integer statusCode;
}
