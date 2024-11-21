package study.withkbo.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.withkbo.user.dto.request.UserRequestDTO;
import study.withkbo.user.dto.response.UserResponseDTO;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;
import study.withkbo.user.service.Mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public User signUp(UserRequestDTO userRequestDTO) {
        User user = userMapper.INSTANCE.userBodyToUser(userRequestDTO);
        user.setUPwd(passwordEncoder.encode(userRequestDTO.getPwd()));
        user.setUStatus("Y");

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getUserList() {
        List<User> userList = userRepository.findAll();
        List<UserResponseDTO> userListDTO = userList.stream().map(UserMapper.INSTANCE::userEntityToUserDTO).collect(Collectors.toList());
        return userListDTO;
    }
}
