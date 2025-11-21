package order.controller;

import edu.fudan.common.client.dto.order.ConsignOrderDto;
import edu.fudan.common.client.dto.order.ConsignPriceDto;
import edu.fudan.common.client.dto.order.FoodOrderDto;
import edu.fudan.common.client.dto.order.OrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignOrder;
import order.entity.ConsignPrice;
import order.mapper.ConsignOrderMapper;
import order.mapper.ConsignPriceMapper;
import order.mapper.FoodOrderMapper;
import order.mapper.OrderMapper;
import order.service.AssuranceOrderService;
import order.service.ConsignPriceService;
import order.service.ConsignOrderService;
import order.service.FoodOrderService;
import order.service.OrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final AssuranceOrderService assuranceService;
    private final ConsignOrderService consignService;
    private final ConsignPriceService consignPriceService;
    private final FoodOrderService foodService;
    private final FoodOrderMapper foodOrderMapper;
    private final ConsignOrderMapper consignMapper;
    private final ConsignPriceMapper consignPriceMapper;

    // ----------------------------- Order -----------------------------
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Response<OrderDto>> deleteOrder(@PathVariable String orderId,
            @RequestHeader HttpHeaders headers) {
        log.info("[deleteOrder][Delete order][OrderId: {}]", orderId);
        return ok(orderService.delete(orderId, headers).map(orderMapper::toDto));
    }

    // ----------------------------- Assurance -----------------------------
    @DeleteMapping(path = "/assurance-orders/{assuranceId}")
    public HttpEntity<Response<UUID>> deleteAssurance(@PathVariable String assuranceId,
            @RequestHeader HttpHeaders headers) {
        log.info("[deleteAssurance][assuranceId: {}]", assuranceId);
        return ok(assuranceService.deleteById(parseUuid(assuranceId), headers));
    }

    @DeleteMapping(path = "/assurance-orders/orderid/{orderId}")
    public HttpEntity<Response<UUID>> deleteAssuranceByOrderId(@PathVariable String orderId,
            @RequestHeader HttpHeaders headers) {
        log.info("[deleteAssuranceByOrderId][orderId: {}]", orderId);
        return ok(assuranceService.deleteByOrderId(parseUuid(orderId), headers));
    }

    // ----------------------------- Consign -----------------------------
    @PutMapping(value = "/consign-orders")
    public HttpEntity<Response<ConsignOrderDto>> updateConsign(@RequestBody ConsignOrderDto request,
            @RequestHeader HttpHeaders headers) {
        log.info("[updateConsign][Update consign order][id: {}]", request.getId());
        Response<ConsignOrder> response = consignService.update(consignMapper.toEntity(request), headers);
        return ok(response.map(consignMapper::toDto));
    }

    // ----------------------------- ConsignPrice -----------------------------
    @PostMapping(path = "/consign-orders/price-configs")
    public HttpEntity<Response<ConsignPriceDto>> createPriceConfig(@RequestBody ConsignPriceDto priceConfig,
            @RequestHeader HttpHeaders headers) {
        log.info("[createPriceConfig][Create price config][config: {}]", priceConfig);
        Response<ConsignPrice> response = consignPriceService.createPriceConfig(
                consignPriceMapper.toEntity(priceConfig), headers);
        return ok(response.map(consignPriceMapper::toDto));
    }

    @PutMapping(path = "/consign-orders/price-configs")
    public HttpEntity<Response<ConsignPriceDto>> updatePriceConfig(@RequestBody ConsignPriceDto priceConfig,
            @RequestHeader HttpHeaders headers) {
        log.info("[updatePriceConfig][Update price config][config: {}]", priceConfig);
        Response<ConsignPrice> response = consignPriceService.updatePriceConfig(
                consignPriceMapper.toEntity(priceConfig), headers);
        return ok(response.map(consignPriceMapper::toDto));
    }

    // ----------------------------- FoodOrder -----------------------------
    @PostMapping(path = "/food-orders/createOrderBatch")
    public HttpEntity<Response<Void>> createFoodBatches(@RequestBody List<FoodOrderDto> foodOrderList,
            @RequestHeader HttpHeaders headers) {
        log.info("[createFoodBatches][Try to Create Food Batches!]");
        List<FoodOrderDto> requestPayload = foodOrderList == null ? Collections.emptyList() : foodOrderList;
        return ok(foodService.createFoodOrdersInBatch(foodOrderMapper.toEntityList(requestPayload), headers));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "/food-orders/{orderId}")
    public HttpEntity<Response<Void>> deleteFoodOrder(@PathVariable String orderId,
            @RequestHeader HttpHeaders headers) {
        log.info("[deleteFoodOrder][Try to Cancel a FoodOrder!]");
        return ok(foodService.deleteFoodOrder(orderId, headers));
    }

    // ----------------------------- Helper Methods -----------------------------
    private static UUID parseUuid(String rawId) {
        return UUID.fromString(rawId);
    }
}
