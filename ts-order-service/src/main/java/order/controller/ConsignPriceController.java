package order.controller;

import edu.fudan.common.client.dto.order.ConsignPriceQueryDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.service.ConsignPriceService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order/consigns/price-configs")
@RequiredArgsConstructor
public class ConsignPriceController {

    private final ConsignPriceService consignPriceService;

    @PostMapping(value = "/calculate")
    public HttpEntity<Response<Double>> calculatePrice(@RequestBody ConsignPriceQueryDto request,
                                                       @RequestHeader HttpHeaders headers) {
        log.info("[calculatePrice][weight: {}, withinRegion: {}]", request.getWeight(), request.isWithinRegion());
        return ok(consignPriceService.getPriceByWeightAndRegion(request.getWeight(),
                request.isWithinRegion(), headers));
    }
}
