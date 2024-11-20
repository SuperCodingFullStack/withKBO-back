package study.withkbo.user.service.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import study.withkbo.user.dto.request.UserBody;
import study.withkbo.user.dto.response.UserDTO;
import study.withkbo.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userBodyToUser(UserBody userBody);

    UserDTO userEntityToUserDTO(User user);

}
