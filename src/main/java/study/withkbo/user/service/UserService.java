package study.withkbo.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.user.dto.request.UserBody;
import study.withkbo.user.dto.response.UserDTO;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserJpaRepository;
import study.withkbo.user.service.Mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public User signUp(UserBody userBody) {
        User user = userMapper.INSTANCE.userBodyToUser(userBody);
        user.setUPwd(passwordEncoder.encode(userBody.getPwd()));
        user.setUStatus("Y");

        return userJpaRepository.save(user);
    }

    public List<UserDTO> getUserList() {
        List<User> userList = userJpaRepository.findAll();
        List<UserDTO> userListDTO = userList.stream().map(UserMapper.INSTANCE::userEntityToUserDTO).collect(Collectors.toList());
        return userListDTO;
    }
}
