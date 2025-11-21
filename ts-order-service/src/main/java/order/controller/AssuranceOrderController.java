package order.controller;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import edu.fudan.common.client.dto.order.CreateAssuranceOrderDto;
import edu.fudan.common.client.dto.order.UpdateAssuranceOrderTypeDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.mapper.AssuranceMapper;
import order.service.AssuranceOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class AssuranceOrderController {

    private final AssuranceOrderService assuranceService;
    private final AssuranceMapper assuranceMapper;

    @PatchMapping(path = "/assurance-orders/{assuranceOrderId}/assurance-type")
    public HttpEntity<Response<AssuranceOrderDto>> changeAssuranceType(@PathVariable String assuranceOrderId,
                                                           @RequestBody UpdateAssuranceOrderTypeDto request,
                                                           @RequestHeader HttpHeaders headers) {
        int typeIndex = request.getTypeIndex();
        log.info("[modifyAssurance][assuranceId: {}, typeIndex: {}]", assuranceOrderId, typeIndex);
        return ok(assuranceService.changeAssuranceType(assuranceOrderId, typeIndex, headers).map(assuranceMapper::toDto));
    }

    @PostMapping(path = "/assurance-orders")
    public HttpEntity<Response<AssuranceOrderDto>> createAssuranceOrder(@RequestBody CreateAssuranceOrderDto request,
                                                              @RequestHeader HttpHeaders headers) {
        int typeIndex = request.getTypeIndex();
        String orderId = request.getOrderId();
        log.info("[createAssuranceOrder][typeIndex: {}, orderId: {}]", typeIndex, orderId);
        return ok(assuranceService.create(typeIndex, orderId, headers).map(assuranceMapper::toDto));
    }
}
