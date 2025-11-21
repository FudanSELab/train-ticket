package order.controller;

import edu.fudan.common.client.dto.order.ModifyOrderStatusDto;
import edu.fudan.common.client.dto.order.OrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.Order;
import order.mapper.ModifyOrderStatusMapper;
import order.mapper.OrderMapper;
import order.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final ModifyOrderStatusMapper modifyOrderStatusMapper;

    @PostMapping(path = "/orders/{orderId}/pay")
    public HttpEntity<Response<OrderDto>> payOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        log.info("[payOrder][Pay Order][OrderId: {}]", orderId);
        return respond(() -> orderService.pay(orderId, headers));
    }

    @PutMapping(path = "/orders/{orderId}/status")
    public HttpEntity<Response<OrderDto>> updateOrderStatus(@PathVariable String orderId,
                                                      @RequestBody ModifyOrderStatusDto request,
                                                      @RequestHeader HttpHeaders headers) {
        log.info("[modifyOrder][Modify Order Status][OrderId: {}]", orderId);
        int status = modifyOrderStatusMapper.toStatus(request);
        return respond(() -> orderService.updateStatus(orderId, status, headers));
    }

    @PostMapping(path = "/orders")
    public HttpEntity<Response<OrderDto>> createNewOrder(@RequestBody OrderDto createOrder,
                                                         @RequestHeader HttpHeaders headers) {
        log.info("[createNewOrder][Create Order][from {} to {} at {}]", createOrder.getFromStationId(), createOrder.getToStationId(), createOrder.getTravelDate());
        return respond(() -> orderService.create(orderMapper.toEntity(createOrder), headers));
    }

    @PutMapping(path = "/orders")
    public HttpEntity<Response<OrderDto>> updateOrder(@RequestBody OrderDto orderInfo,
                                                        @RequestHeader HttpHeaders headers) {
        log.info("[saveChanges][Save Order Info][OrderId:{}]", orderInfo.getId());
        return respond(() -> orderService.update(orderMapper.toEntity(orderInfo), headers));
    }

    private Response<OrderDto> toOrderDtoResponse(Response<Order> response) {
        if (response == null) {
            return null;
        }
        OrderDto orderDto = response.getData() == null ? null : orderMapper.toDto(response.getData());
        return new Response<>(response.getStatus(), response.getMsg(), orderDto);
    }

    private HttpEntity<Response<OrderDto>> respond(Supplier<Response<Order>> action) {
        return ok(toOrderDtoResponse(action.get()));
    }
}
