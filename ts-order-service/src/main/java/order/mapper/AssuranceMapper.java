package order.mapper;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import order.entity.Assurance;
import order.entity.AssuranceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for Assurance related DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface AssuranceMapper {

    @Mapping(target = "typeIndex", expression = "java(mapTypeIndex(assurance.getType()))")
    @Mapping(target = "typeName", expression = "java(mapTypeName(assurance.getType()))")
    @Mapping(target = "typePrice", expression = "java(mapTypePrice(assurance.getType()))")
    AssuranceOrderDto toDto(Assurance assurance);

    List<AssuranceOrderDto> toDtoList(List<Assurance> assurances);

    default int mapTypeIndex(AssuranceType type) {
        return type == null ? 0 : type.getIndex();
    }

    default String mapTypeName(AssuranceType type) {
        return type == null ? null : type.getName();
    }

    default double mapTypePrice(AssuranceType type) {
        return type == null ? 0.0 : type.getPrice();
    }
}


