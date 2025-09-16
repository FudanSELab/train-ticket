package auth.mapper;

import auth.entity.User;
import edu.fudan.common.client.dto.user.UserDto;
import org.mapstruct.Mapper;
import java.util.List;

/**
 * MapStruct mapper for Auth Service's User entity and UserDto.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
}
