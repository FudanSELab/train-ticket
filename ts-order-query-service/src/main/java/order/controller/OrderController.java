package order.controller;

import edu.fudan.common.client.dto.order.OrderQueryDto;
import edu.fudan.common.client.dto.order.SoldOrderStatDto;
import edu.fudan.common.client.dto.order.TravelDateNumberDto;
import edu.fudan.common.entity.OrderSecurity;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.Order;
import order.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/order-query")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/orders/{orderId}")
    public HttpEntity<Response<Order>> getOrderById(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        log.info("[getOrderById][Get Order By Id][OrderId: {}]", orderId);
        return ok(orderService.getOrderById(orderId, headers));
    }

    @PostMapping(path = "/orders/query")
    public HttpEntity<Response<ArrayList<Order>>> queryOrders(@RequestBody OrderQueryDto qi,
                                  @RequestHeader HttpHeaders headers) {
        log.info("[queryOrders][Query Orders][for LoginId :{}]", qi.getLoginId());
        return ok(orderService.queryOrders(qi, qi.getLoginId(), headers));
    }

    @PostMapping(path = "/orders/get-by-travel-date-number")
    public HttpEntity<Response<SoldOrderStatDto>> queryAlreadySoldOrders(@RequestBody TravelDateNumberDto request,
                                          @RequestHeader HttpHeaders headers) {
        log.info("[queryAlreadySoldOrders][Calculate Sold Tickets][Date: {} TrainNumber: {}]", request.getTravelDate(), request.getTravelNumber());
        return ok(orderService.queryAlreadySoldOrders(request, headers));
    }

    @GetMapping(path = "/orders/{orderId}/price")
    public HttpEntity<Response<String>> getOrderPrice(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        log.info("[getOrderPrice][Get Order Price][OrderId: {}]", orderId);
        // String
        return ok(orderService.getOrderPrice(orderId, headers));
    }

    @GetMapping(path = "/orders/security")
    public HttpEntity<Response<OrderSecurity>> securityInfoCheck(@RequestParam String checkDate, @RequestParam String userId,
                                        @RequestHeader HttpHeaders headers) {
        log.info("[checkSecurityAboutOrder][Security Info Get][AccountId:{}]", userId);
        return ok(orderService.checkSecurityAboutOrder(StringUtils.String2Date(checkDate), userId, headers));
    }
}
