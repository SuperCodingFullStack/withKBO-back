package study.withkbo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.dto.request.UserLoginRequestDto;
import study.withkbo.user.dto.request.UserSignUpRequestDto;
import study.withkbo.user.dto.response.UserResponseDto;
import study.withkbo.user.entity.User;
import study.withkbo.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //없어도 되는 메소드지만 스웨거 상 사용하려고 만들어놨습니다.
    @PostMapping("/login")
    public void login(@RequestBody UserLoginRequestDto requestDto) {
    }

    @PostMapping("/signUp")
    public ApiResponseDto<UserResponseDto> signUp(@RequestBody UserSignUpRequestDto requestDto) {

        UserResponseDto result = userService.signUp(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }

    @GetMapping("/userList")
    public ApiResponseDto<List<UserResponseDto>>getUserList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user =userDetails.getUser();
        System.out.println(user.toString());
        return null;
    }
}
