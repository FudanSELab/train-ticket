package order.controller;

import edu.fudan.common.client.dto.order.ConsignOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignOrder;
import order.mapper.ConsignOrderMapper;
import order.service.ConsignOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class ConsignOrderController {

    private final ConsignOrderService service;
    private final ConsignOrderMapper consignMapper;

    @PostMapping(value = "/consign-orders")
    public HttpEntity<Response<ConsignOrderDto>> insertConsign(@RequestBody ConsignOrderDto request,
                                    @RequestHeader HttpHeaders headers) {
        log.info("[insertConsign][Insert consign order: {}]", request);
        Response<ConsignOrder> response = service.create(consignMapper.toEntity(request), headers);
        return ok(response.map(consignMapper::toDto));
    }
}
