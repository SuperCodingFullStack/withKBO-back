package study.withkbo.user.service.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import study.withkbo.user.dto.request.UserRequestDTO;
import study.withkbo.user.dto.response.UserResponseDTO;
import study.withkbo.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source="email", target="uEmail")
    @Mapping(source="pwd", target="uPwd")
    @Mapping(source="name", target="uName")
    @Mapping(source="nickname", target="uNickname")
    @Mapping(source="phone", target="uPhone")
    @Mapping(source="phoneAuth", target="uPhoneAuth")
    @Mapping(source="address", target="uAddress")
    User userBodyToUser(UserRequestDTO userRequestDTO);

    UserResponseDTO userEntityToUserDTO(User user);

}
