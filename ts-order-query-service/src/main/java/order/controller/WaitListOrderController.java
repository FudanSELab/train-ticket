package order.controller;

import edu.fudan.common.client.dto.order.WaitListOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.mapper.WaitListOrderMapper;
import order.service.WaitListOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/")
    public HttpEntity<Response<List<WaitListOrderDto>>> getAllOrders(@RequestHeader HttpHeaders headers) {
        log.info("[getAllOrders][Get All Orders]");
        return ok(waitListOrderService.getAllWaitListOrders(headers).map(waitListOrderMapper::toDtoList));
    }

    @GetMapping(path = "/active")
    public HttpEntity<Response<List<WaitListOrderDto>>> getWaitListOrders(@RequestHeader HttpHeaders headers) {
        log.info("[getWaitListOrders][Get All Wait List Orders]");
        return ok(waitListOrderService.getAllActiveWaitListOrders(headers).map(waitListOrderMapper::toDtoList));
    }

}
