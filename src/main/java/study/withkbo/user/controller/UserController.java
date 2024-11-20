package study.withkbo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.withkbo.user.dto.request.UserBody;
import study.withkbo.user.dto.response.UserDTO;
import study.withkbo.user.entity.User;
import study.withkbo.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody UserBody userBody) {
        return userService.signUp(userBody);
    }

    @GetMapping("/userList")
    public List<UserDTO> getUserList() {
        return userService.getUserList();
    }
}
