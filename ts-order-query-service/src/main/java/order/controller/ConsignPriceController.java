package order.controller;

import edu.fudan.common.client.dto.order.ConsignPriceDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignPrice;
import order.mapper.ConsignPriceMapper;
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
    private final ConsignPriceMapper consignPriceMapper;

    @GetMapping(value = "/info")
    public HttpEntity<Response<String>> getPriceInfo(@RequestHeader HttpHeaders headers) {
        log.info("[getPriceInfo][Get price info]");
        return ok(consignPriceService.queryPriceInformation(headers));
    }

    @GetMapping(value = "/")
    public HttpEntity<Response<ConsignPriceDto>> getPriceConfig(@RequestHeader HttpHeaders headers) {
        log.info("[getPriceConfig][Get price config]");
        Response<ConsignPrice> response = consignPriceService.getPriceConfig(headers);
        return ok(response.map(consignPriceMapper::toDto));
    }

}
