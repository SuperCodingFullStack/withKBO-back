package study.withkbo.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.security.JwtAuthenticationFilter;
import study.withkbo.user.dto.request.UserLoginRequestDto;
import study.withkbo.user.dto.request.UserPasswordRequestDto;
import study.withkbo.user.dto.request.UserSignUpRequestDto;
import study.withkbo.user.dto.response.UserResponseDto;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(UserLoginRequestDto requestDto) {


    }

    public UserResponseDto signUp(UserSignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        checkUsername(username);

        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_EMAIL);
        }


        User user = userRepository.save(User.builder()
                .username(username)
                .email(requestDto.getEmail())
                .password(password)
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .profileImg(requestDto.getProfileImg()).build());

        return new UserResponseDto(user);
    }

    public void checkPassword(String inputPassword, String password) {
        if(!passwordEncoder.matches(inputPassword, password)) {
            throw new CommonException(CommonError.USER_PASSWORD_WRONG);
        }
    }

    @Transactional
    public UserResponseDto updatePassword(UserPasswordRequestDto requestDto, User user) {
        checkPassword(requestDto.getCheckPassword(), user.getPassword());
        user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_USERNAME);
        }
    }

    public void checkEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if(checkEmail.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_EMAIL);
        }
    }
}
