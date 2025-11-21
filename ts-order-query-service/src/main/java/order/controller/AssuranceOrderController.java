package order.controller;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import edu.fudan.common.client.dto.order.AssuranceTypeDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.mapper.AssuranceOrderMapper;
import order.service.AssuranceOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class AssuranceOrderController {

    private final AssuranceOrderService assuranceService;
    private final AssuranceOrderMapper assuranceMapper;
    @GetMapping(path = "/assurance-orders/types")
    public HttpEntity<Response<List<AssuranceTypeDto>>> getAllAssuranceType(@RequestHeader HttpHeaders headers) {
        log.info("[getAllAssuranceType]");
        return ok(assuranceService.getAllAssuranceTypes(headers));
    }

    @GetMapping(path = "/assurance-orders/{assuranceOrderId}")
    public HttpEntity<Response<Optional<AssuranceOrderDto>>> getAssuranceById(@PathVariable String assuranceOrderId, @RequestHeader HttpHeaders headers) {
        log.info("[getAssuranceById][assuranceOrderId: {}]", assuranceOrderId);
        return ok(assuranceService.findAssuranceById(parseUuid(assuranceOrderId), headers).map(optional -> optional.map(assuranceMapper::toDto)));
    }

    private static UUID parseUuid(String rawId) {
        return UUID.fromString(rawId);
    }
}
