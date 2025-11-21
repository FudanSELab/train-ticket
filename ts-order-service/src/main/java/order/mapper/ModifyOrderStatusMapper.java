package order.mapper;

import edu.fudan.common.client.dto.order.ModifyOrderStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper dedicated to {@link ModifyOrderStatusDto} conversions.
 */
@Mapper(componentModel = "spring")
public interface ModifyOrderStatusMapper {

    /**
     * Extracts the status value from the incoming DTO.
     *
     * @param dto request payload
     * @return target order status code
     */
    default int toStatus(ModifyOrderStatusDto dto) {
        return dto == null ? 0 : dto.getStatus();
    }

    /**
     * Builds a DTO from the provided status code.
     *
     * @param status current order status
     * @return dto representation
     */
    @Mapping(target = "status", source = "status")
    ModifyOrderStatusDto fromStatus(Integer status);
}


