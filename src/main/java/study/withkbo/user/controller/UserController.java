package study.withkbo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.withkbo.user.dto.request.UserRequestDTO;
import study.withkbo.user.dto.response.UserResponseDTO;
import study.withkbo.user.entity.User;
import study.withkbo.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.signUp(userRequestDTO);
    }

    @GetMapping("/userList")
    public List<UserResponseDTO> getUserList() {
        return userService.getUserList();
    }
}
