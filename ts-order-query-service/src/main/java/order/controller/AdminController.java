package order.controller;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.service.AssuranceOrderService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/order-query/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AssuranceOrderService assuranceService;

    // ----------------------------- Assurance -----------------------------
    @GetMapping(path = "/assurance-orders")
    public HttpEntity<Response<List<AssuranceOrderDto>>> getAllAssurances(@RequestHeader HttpHeaders headers) {
        log.info("[getAllAssurances]");
        return ok(assuranceService.getAllAssurances(headers));
    }
}
