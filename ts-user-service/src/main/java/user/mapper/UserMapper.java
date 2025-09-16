package user.mapper;

import org.mapstruct.Mapper;
import user.entity.User;
import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import java.util.List;

/**
 * MapStruct mapper for converting between {@link UserDto} and {@link User}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    UserDto toDto(User user);
    Response<UserDto> toDtoResponse(Response<User> resp);
    Response<User> toEntityResponse(Response<UserDto> resp);
    Response<List<UserDto>> toDtoListResponse(Response<List<User>> resp);
    Response<List<User>> toEntityListResponse(Response<List<UserDto>> resp);
}
