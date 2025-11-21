package order.mapper;

import edu.fudan.common.client.dto.order.FoodOrderDto;
import order.entity.FoodOrder;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link FoodOrder} and {@link FoodOrderDto}.
 */
@Mapper(componentModel = "spring")
public interface FoodOrderMapper {

    FoodOrderDto toDto(FoodOrder foodOrder);

    List<FoodOrderDto> toDtoList(List<FoodOrder> foodOrders);

    FoodOrder toEntity(FoodOrderDto foodOrderDto);

    List<FoodOrder> toEntityList(List<FoodOrderDto> foodOrderDtos);
}


