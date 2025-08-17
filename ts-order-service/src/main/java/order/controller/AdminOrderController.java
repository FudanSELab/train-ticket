package order.controller;

import order.entity.Order;
import edu.fudan.common.util.Response;
import order.service.AdminOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/orderservice/admin")
public class AdminOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminOrderController.class);

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [Admin Order Service] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/adminorder")
    public ResponseEntity<Response> getAllOrders(@RequestHeader HttpHeaders headers) {
        LOGGER.info("[getAllOrders][Get all orders]");
        return ok(adminOrderService.getAllOrders(headers));
    }

    @PostMapping("/adminorder")
    public ResponseEntity<Response> addOrder(@RequestBody Order request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addOrder][Add new order][AccountID: {}]", request.getAccountId());
        return ok(adminOrderService.addOrder(request, headers));
    }

    @PutMapping("/adminorder")
    public ResponseEntity<Response> updateOrder(@RequestBody Order request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[updateOrder][Update order][OrderId: {}]", request.getId());
        return ok(adminOrderService.updateOrder(request, headers));
    }

    @DeleteMapping("/adminorder/{orderId}/{trainNumber}")
    public ResponseEntity<Response> deleteOrder(@PathVariable String orderId,
                                                @PathVariable String trainNumber,
                                                @RequestHeader HttpHeaders headers) {
        LOGGER.info("[deleteOrder][Delete order][OrderId: {}]", orderId);
        return ok(adminOrderService.deleteOrder(orderId, trainNumber, headers));
    }
}
