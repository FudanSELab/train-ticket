package order.controller;

import edu.fudan.common.client.dto.order.FoodOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.mapper.FoodOrderMapper;
import order.service.FoodOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order/food-orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService foodService;
    private final FoodOrderMapper foodOrderMapper;

    @GetMapping(path = "/")
    public HttpEntity<Response<List<FoodOrderDto>>> findAllFoodOrder(@RequestHeader HttpHeaders headers) {
        log.info("[Food Service]Try to Find all FoodOrder!");
        return ok(foodService.findAllFoodOrder(headers).map(foodOrderMapper::toDtoList));
    }

    @GetMapping(path = "/{orderId}")
    public HttpEntity<Response<FoodOrderDto>> findFoodOrderByOrderId(@PathVariable String orderId,
                                                                     @RequestHeader HttpHeaders headers) {
        log.info("[findFoodOrderByOrderId][Try to Find FoodOrder By orderId!][orderId: {}]", orderId);
        return ok(foodService.findByOrderId(orderId, headers).map(foodOrderMapper::toDto));
    }

}
