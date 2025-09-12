package user.mapper;

import org.mapstruct.Mapper;
import user.entity.User;
import edu.fudan.common.client.dto.user.UserDto;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between {@link UserDto} and {@link User}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User user);
}
