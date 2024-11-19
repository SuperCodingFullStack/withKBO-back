package study.withkbo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.withkbo.user.dto.UserBody;
import study.withkbo.user.entity.User;
import study.withkbo.user.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private static UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody UserBody userBody) {
        User user = userService.signUp(userBody);
    }
}
