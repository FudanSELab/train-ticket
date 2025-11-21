package order.controller;

import edu.fudan.common.client.dto.order.ConsignOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import edu.fudan.common.client.dto.order.GetConsignOrdersByConsigneeDto;
import edu.fudan.common.client.dto.order.GetConsignOrdersByUserIdDto;
import order.entity.ConsignOrder;
import order.mapper.ConsignOrderMapper;
import order.service.ConsignOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping(value = "/consign-orders/get-by-user-id")
    public HttpEntity<Response<List<ConsignOrderDto>>> findByUserId(@RequestBody GetConsignOrdersByUserIdDto request,
                                                                    @RequestHeader HttpHeaders headers) {
        log.info("[findByUserId][Find consign by user id][id: {}]", request.getUserId());
        Response<List<ConsignOrder>> response = service.getByUserId(UUID.fromString(request.getUserId()), headers);
        return ok(response.map(consignMapper::toDtoList));
    }

    @GetMapping(value = "/consign-orders/{id}")
    public HttpEntity<Response<ConsignOrderDto>> findByOrderId(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        log.info("[findByOrderId][Find consign by order id][id: {}]", id);
        Response<ConsignOrder> response = service.getByOrderId(UUID.fromString(id), headers);
        return ok(response.map(consignMapper::toDto));
    }

    @PostMapping(value = "/consign-orders/get-by-consignee")
    public HttpEntity<Response<List<ConsignOrderDto>>> findByConsignee(@RequestBody GetConsignOrdersByConsigneeDto request,
                                                                       @RequestHeader HttpHeaders headers) {
        log.info("[findByConsignee][Find consign by consignee][consignee: {}]", request.getConsignee());
        Response<List<ConsignOrder>> response = service.getByConsignee(request.getConsignee(), headers);
        return ok(response.map(consignMapper::toDtoList));
    }
}
