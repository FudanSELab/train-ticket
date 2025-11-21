package order.mapper;

import edu.fudan.common.client.dto.order.ConsignOrderDto;
import order.entity.ConsignOrder;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for converting between Consign entities/records and
 * {@link ConsignOrderDto}.
 */
@Mapper(componentModel = "spring")
public interface ConsignOrderMapper {

  ConsignOrderDto toDto(ConsignOrder c);

  List<ConsignOrderDto> toDtoList(List<ConsignOrder> cs);

  ConsignOrder toEntity(ConsignOrderDto dto);
}
