package order.service.impl;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import edu.fudan.common.client.dto.order.AssuranceTypeDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.AssuranceOrder;
import order.entity.AssuranceType;
import order.mapper.AssuranceOrderMapper;
import order.repository.AssuranceRepository;
import order.service.AssuranceOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssuranceOrderServiceImpl implements AssuranceOrderService {

    private final AssuranceRepository assuranceRepository;
    private final AssuranceOrderMapper assuranceMapper;

    @Override
    public Response<Optional<AssuranceOrder>> findAssuranceById(UUID id, HttpHeaders headers) {
        Optional<AssuranceOrder> assurance = assuranceRepository.findById(id.toString());
        if (assurance.isPresent()) {
            log.info("[findAssuranceById][Success][assurance id: {}]", id);
            return success("Find Assurance Success", assurance);
        }
        log.warn("[findAssuranceById][No content][assurance id: {}]", id);
        return failure("No Content by this id", null);
    }

    @Override
    public Response<AssuranceOrder> findAssuranceByOrderId(UUID orderId, HttpHeaders headers) {
        AssuranceOrder assurance = assuranceRepository.findByOrderId(orderId.toString());
        if (assurance != null) {
            log.info("[findAssuranceByOrderId][Success][orderId: {}]", orderId);
            return success("Find Assurance Success", assurance);
        }
        log.warn("[findAssuranceByOrderId][No content][orderId: {}]", orderId);
        return failure("No Content by this orderId", null);
    }

    @Override
    public Response<List<AssuranceOrderDto>> getAllAssurances(HttpHeaders headers) {
        List<AssuranceOrder> list = assuranceRepository.findAll();
        if (list == null || list.isEmpty()) {
            log.warn("[getAllAssurances][No content]");
            return failure("No Content, Assurance is empty", null);
        }

        List<AssuranceOrderDto> result = assuranceMapper.toDtoList(list);
        log.info("[getAllAssurances][Success][size: {}]", list.size());
        return success("Success", result);
    }

    @Override
    public Response<List<AssuranceTypeDto>> getAllAssuranceTypes(HttpHeaders headers) {
        List<AssuranceTypeDto> types = Arrays.stream(AssuranceType.values())
                .map(this::toAssuranceTypeDto)
                .collect(Collectors.toList());
        if (!types.isEmpty()) {
            log.info("[getAllAssuranceTypes][Success][size: {}]", types.size());
            return success("Find All Assurance", types);
        }
        log.warn("[getAllAssuranceTypes][No content]");
        return failure("Assurance is Empty", null);
    }

    private AssuranceTypeDto toAssuranceTypeDto(AssuranceType type) {
        AssuranceTypeDto dto = new AssuranceTypeDto();
        dto.setIndex(type.getIndex());
        dto.setName(type.getName());
        dto.setPrice(type.getPrice());
        return dto;
    }

    private static <T> Response<T> success(String msg, T data) {
        return new Response<>(1, msg, data);
    }

    private static <T> Response<T> failure(String msg, T data) {
        return new Response<>(0, msg, data);
    }
}
