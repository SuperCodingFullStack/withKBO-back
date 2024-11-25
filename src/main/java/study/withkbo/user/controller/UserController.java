package study.withkbo.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.dto.request.UserLoginRequestDto;
import study.withkbo.user.dto.request.UserPasswordRequestDto;
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
    public ApiResponseDto<UserResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) {

        UserResponseDto result = userService.signUp(requestDto);
        return ApiResponseDto.success(MessageType.CREATE, result);
    }


    //임시 테스트 코드
    @GetMapping("/userList")
    public ApiResponseDto<List<UserResponseDto>>getUserList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user =userDetails.getUser();
        return null;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/password")
    public ApiResponseDto<?> checkPassword(@RequestBody String password, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.checkPassword(password, userDetails.getPassword());
        return ApiResponseDto.success(MessageType.RETRIEVE, "비밀번호 확인이 완료 되었습니다.");
    }

    @PutMapping("/password")
    public ApiResponseDto<UserResponseDto> updatePassword(@Valid @RequestBody UserPasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto result = userService.updatePassword(requestDto, userDetails.getUser());
        return ApiResponseDto.success(MessageType.UPDATE, result);
    }

    @GetMapping("/username")
    public ApiResponseDto<String> checkUsername(@RequestParam String username){
        userService.checkUsername(username);
        return ApiResponseDto.success(MessageType.RETRIEVE, "아이디 중복 확인이 완료되었습니다. ");
    }


}
