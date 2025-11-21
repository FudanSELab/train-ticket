package order.mapper;

import edu.fudan.common.client.dto.order.WaitListOrderDto;
import edu.fudan.common.util.StringUtils;
import order.entity.WaitListOrder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper converting between {@link WaitListOrder} and {@link WaitListOrderDto}.
 */
@Component
public class WaitListOrderMapper {

    public WaitListOrderDto toDto(WaitListOrder waitListOrder) {
        if (waitListOrder == null) {
            return null;
        }

        return WaitListOrderDto.builder()
                .id(waitListOrder.getId())
                .travelTime(formatDate(waitListOrder.getTravelTime()))
                .userId(waitListOrder.getUserId())
                .contactsId(waitListOrder.getContactsId())
                .contactsName(waitListOrder.getContactsName())
                .contactsDocumentType(waitListOrder.getContactsDocumentType())
                .contactsDocumentNumber(waitListOrder.getContactsDocumentNumber())
                .trainNumber(waitListOrder.getTrainNumber())
                .seatType(waitListOrder.getSeatType())
                .fromStation(waitListOrder.getFromStation())
                .toStation(waitListOrder.getToStation())
                .price(waitListOrder.getPrice())
                .waitUtilTime(formatDate(waitListOrder.getWaitUtilTime()))
                .createdTime(formatDate(waitListOrder.getCreatedTime()))
                .status(waitListOrder.getStatus())
                .build();
    }

    public List<WaitListOrderDto> toDtoList(List<WaitListOrder> waitListOrders) {
        if (waitListOrders == null) {
            return Collections.emptyList();
        }
        return waitListOrders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public WaitListOrder toEntity(WaitListOrderDto dto) {
        if (dto == null) {
            return null;
        }
        WaitListOrder entity = new WaitListOrder();
        entity.setId(dto.getId() == null ? UUID.randomUUID().toString() : dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setContactsId(dto.getContactsId());
        entity.setContactsName(dto.getContactsName());
        entity.setContactsDocumentType(dto.getContactsDocumentType());
        entity.setContactsDocumentNumber(dto.getContactsDocumentNumber());
        entity.setTrainNumber(dto.getTrainNumber());
        entity.setSeatType(dto.getSeatType());
        entity.setFromStation(dto.getFromStation());
        entity.setToStation(dto.getToStation());
        entity.setPrice(dto.getPrice());
        if (dto.getTravelTime() != null) {
            entity.setTravelTime(parseDate(dto.getTravelTime()));
        }
        if (dto.getWaitUtilTime() != null) {
            entity.setWaitUntilTime(parseDate(dto.getWaitUtilTime()));
        }
        if (dto.getCreatedTime() != null) {
            entity.setCreatedTime(parseDate(dto.getCreatedTime()));
        }
        if (dto.getStatus() != 0) {
            entity.setStatus(dto.getStatus());
        }
        return entity;
    }

    private static String formatDate(Date date) {
        return date == null ? null : StringUtils.Date2String(date);
    }

    private static Date parseDate(String value) {
        return value == null ? null : StringUtils.String2Date(value);
    }
}

