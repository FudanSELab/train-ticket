package train.mapper;

import edu.fudan.common.client.dto.train.TrainTypeDto;
import edu.fudan.common.util.Response;
import org.mapstruct.Mapper;
import train.entity.TrainType;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface TrainTypeMapper {

    TrainType toEntity(TrainTypeDto dto);

    TrainTypeDto toDto(TrainType entity);

    List<TrainTypeDto> toDtoList(List<TrainType> entities);

    List<TrainType> toEntityList(List<TrainTypeDto> entities);

    Map<String, TrainTypeDto> toDtoMap(Map<String, TrainType> entityMap);

    Map<String, TrainType> toEntityMap(Map<String, TrainTypeDto> dtoMap);

    default Response<TrainTypeDto> toDtoResponse(Response<TrainType> resp) {
        if (resp == null) {
            return null;
        }
        return new Response<>(resp.getStatus(), resp.getMsg(), toDto(resp.getData()));
    }

    default Response<List<TrainTypeDto>> toDtoListResponse(Response<List<TrainType>> resp) {
        if (resp == null) {
            return null;
        }
        List<TrainTypeDto> data = resp.getData() == null ? null : toDtoList(resp.getData());
        return new Response<>(resp.getStatus(), resp.getMsg(), data);
    }

    default Response<Map<String, TrainTypeDto>> toDtoMapResponse(Response<Map<String, TrainType>> resp) {
        if (resp == null) {
            return null;
        }
        Map<String, TrainTypeDto> data = resp.getData() == null ? null : toDtoMap(resp.getData());
        return new Response<>(resp.getStatus(), resp.getMsg(), data);
    }
}

