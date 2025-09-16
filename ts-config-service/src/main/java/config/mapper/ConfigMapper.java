package config.mapper;

import config.entity.Config;
import edu.fudan.common.client.dto.config.ConfigDto;
import edu.fudan.common.util.Response;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link ConfigDto} and {@link Config}.
 */
@Mapper(componentModel = "spring")
public interface ConfigMapper {
    Config toEntity(ConfigDto dto);
    ConfigDto toDto(Config entity);

    Response<ConfigDto> toDtoResponse(Response<Config> resp);
    Response<Config> toEntityResponse(Response<ConfigDto> resp);

    Response<List<ConfigDto>> toDtoListResponse(Response<List<Config>> resp);
    Response<List<Config>> toEntityListResponse(Response<List<ConfigDto>> resp);
}
