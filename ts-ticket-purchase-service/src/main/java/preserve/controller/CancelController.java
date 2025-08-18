package preserve.controller;

import preserve.service.CancelService;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Cancel endpoints migrated from ts-cancel-service.
 */
@RestController
@RequestMapping("/api/v1/ticket-purchase/cancel")
public class CancelController {

    @Autowired
    private CancelService cancelService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Cancel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/refound/{orderId}")
    public HttpEntity<?> calculate(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[calculate][Calculate Cancel Refund][OrderId: {}]", orderId);
        return ok(cancelService.calculateRefund(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/{orderId}/{loginId}")
    public HttpEntity<?> cancelTicket(@PathVariable String orderId, @PathVariable String loginId,
                                      @RequestHeader HttpHeaders headers) {
        LOGGER.info("[cancelTicket][Cancel Ticket][OrderId: {}]", orderId);
        try {
            LOGGER.info("[cancelTicket][Verify Success]");
            return ok(cancelService.cancelOrder(orderId, loginId, headers));
        } catch (Exception e) {
            LOGGER.error("[cancelTicket][Exception]", e);
            return ok(new Response<>(1, "error", null));
        }
    }
}
