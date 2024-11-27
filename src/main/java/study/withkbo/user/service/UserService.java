package study.withkbo.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.user.dto.request.UserPasswordRequestDto;
import study.withkbo.user.dto.request.UserSignUpRequestDto;
import study.withkbo.user.dto.request.UserUpdateRequestDto;
import study.withkbo.user.dto.response.UserResponseDto;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserResponseDto signUp(UserSignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        checkUsername(username);

        User user = userRepository.save(User.builder()
                .username(username)
                .password(password)
                .nickname(requestDto.getNickname())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .profileImg(requestDto.getProfileImg())
                .team(requestDto.getTeam()).build());

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
        Optional<User> checkUsername = userRepository.findByUsernameAndIsDeletedFalse(username);
        if (checkUsername.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_USERNAME);
        }
    }

    public void withdraw(User user) {
        user.withdraw();
        userRepository.save(user);
    }

    public void checkNickname(String nickname) {
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if(checkNickname.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_USERNAME);
        }
    }

    public UserResponseDto updateUserInfo(UserUpdateRequestDto requestDto, User user) {
        user.updateUser(requestDto);
        return new UserResponseDto(userRepository.save(user));
    }
}
