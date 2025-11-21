package order.mapper;

import edu.fudan.common.client.dto.order.ConsignPriceDto;
import order.entity.ConsignPrice;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for converting between ConsignPrice entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface ConsignPriceMapper {

    ConsignPriceDto toDto(ConsignPrice entity);

    ConsignPrice toEntity(ConsignPriceDto dto);
}


