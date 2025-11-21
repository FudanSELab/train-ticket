package order.controller;

import edu.fudan.common.client.dto.order.WaitListOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.WaitListOrder;
import order.mapper.WaitListOrderMapper;
import order.service.WaitListOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order/wait-list-orders")
@RequiredArgsConstructor
public class WaitListOrderController {

    private final WaitListOrderService waitListOrderService;
    private final WaitListOrderMapper waitListOrderMapper;

    @PostMapping(path = "/")
    public HttpEntity<Response<WaitListOrderDto>> createNewOrder(@RequestBody WaitListOrderDto createOrder,
            @RequestHeader HttpHeaders headers) {
        log.info("[createWaitOrder][Create Wait Order][from {} to {} at {}]",
                createOrder.getFromStation(),
                createOrder.getToStation(),
                createOrder.getTravelTime());
        WaitListOrder waitListOrder = waitListOrderMapper.toEntity(createOrder);
        return ok(waitListOrderService.create(waitListOrder, headers).map(waitListOrderMapper::toDto));
    }

}
