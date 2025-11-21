package order.controller;

import edu.fudan.common.client.dto.order.FoodOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.FoodOrder;
import order.mapper.FoodOrderMapper;
import order.service.FoodOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order/food-orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService foodService;
    private final FoodOrderMapper foodOrderMapper;

    @PostMapping(path = "/")
    public HttpEntity<Response<FoodOrderDto>> createFoodOrder(@RequestBody FoodOrderDto addFoodOrder,
                                                              @RequestHeader HttpHeaders headers) {
        log.info("[createFoodOrder][Try to Create a FoodOrder!]");
        FoodOrder request = foodOrderMapper.toEntity(addFoodOrder);
        return ok(foodService.createFoodOrder(request, headers).map(foodOrderMapper::toDto));
    }

    @PutMapping(path = "/")
    public HttpEntity<Response<FoodOrderDto>> updateFoodOrder(@RequestBody FoodOrderDto updateFoodOrder,
                                                              @RequestHeader HttpHeaders headers) {
        log.info("[updateFoodOrder][Try to Update a FoodOrder!]");
        FoodOrder request = foodOrderMapper.toEntity(updateFoodOrder);
        return ok(foodService.updateFoodOrder(request, headers).map(foodOrderMapper::toDto));
    }
}
