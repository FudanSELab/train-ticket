package price.mapper;

import edu.fudan.common.client.dto.ticket.PriceDto;
import edu.fudan.common.util.Response;
import org.mapstruct.Mapper;
import price.entity.Price;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toEntity(PriceDto dto);

    PriceDto toDto(Price entity);

    List<PriceDto> toDtoList(List<Price> entities);

    Map<String, PriceDto> toDtoMap(Map<String, Price> entityMap);

    default Response<PriceDto> toDtoResponse(Response<Price> resp) {
        if (resp == null) {
            return null;
        }
        return new Response<>(resp.getStatus(), resp.getMsg(), toDto(resp.getData()));
    }

    default Response<List<PriceDto>> toDtoListResponse(Response<List<Price>> resp) {
        if (resp == null) {
            return null;
        }
        List<PriceDto> data = resp.getData() == null ? null : toDtoList(resp.getData());
        return new Response<>(resp.getStatus(), resp.getMsg(), data);
    }

    default Response<Map<String, PriceDto>> toDtoMapResponse(Response<Map<String, Price>> resp) {
        if (resp == null) {
            return null;
        }
        Map<String, PriceDto> data = resp.getData() == null ? null : toDtoMap(resp.getData());
        return new Response<>(resp.getStatus(), resp.getMsg(), data);
    }
}
