package order.mapper;

import edu.fudan.common.client.dto.order.OrderDto;
import order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct mapper for converting between {@link Order} entities and {@link OrderDto}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    Order toEntity(OrderDto orderDto);
}


