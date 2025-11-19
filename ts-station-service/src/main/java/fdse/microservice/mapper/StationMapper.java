package fdse.microservice.mapper;

import edu.fudan.common.client.dto.station.StationDto;
import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    Station toEntity(StationDto dto);

    StationDto toDto(Station entity);

    List<Station> toEntityList(List<StationDto> dtoList);

    List<StationDto> toDtoList(List<Station> entityList);

    default Response<StationDto> toDtoResponse(Response<Station> response) {
        if (response == null) {
            return null;
        }
        StationDto dto = response.getData() == null ? null : toDto(response.getData());
        return new Response<>(response.getStatus(), response.getMsg(), dto);
    }

    default Response<List<StationDto>> toDtoListResponse(Response<List<Station>> response) {
        if (response == null) {
            return null;
        }
        List<StationDto> dtoList = response.getData() == null ? null : toDtoList(response.getData());
        return new Response<>(response.getStatus(), response.getMsg(), dtoList);
    }
}

