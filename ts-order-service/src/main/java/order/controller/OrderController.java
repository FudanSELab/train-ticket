package order.controller;

import order.entity.*;
import order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/orderservice")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Order Service ] !";
    }

    /***************************For Normal Use***************************/

    @PostMapping(value = "/order/tickets")
    public HttpEntity getTicketListByDateAndTripId(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Get Sold Ticket] Date: {}", seatRequest.getTravelDate().toString());
        return ok(orderService.getSoldTickets(seatRequest, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order")
    public HttpEntity createNewOrder(@RequestBody Order createOrder, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Create Order] Create Order form {} ---> {} at {}", createOrder.getFrom(), createOrder.getTo(), createOrder.getTravelDate());
        OrderController.LOGGER.info("[Verify Login] Success");
        return ok(orderService.create(createOrder, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/admin")
    public HttpEntity addcreateNewOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        return ok(orderService.addNewOrder(order, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/query")
    public HttpEntity queryOrders(@RequestBody OrderInfo qi,
                                  @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Query Orders] Query Orders for {}", qi.getLoginId());
        OrderController.LOGGER.info("[Verify Login] Success");
        return ok(orderService.queryOrders(qi, qi.getLoginId(), headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/order/refresh")
    public HttpEntity queryOrdersForRefresh(@RequestBody OrderInfo qi,
                                            @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Query Orders] Query Orders for {}", qi.getLoginId());
        return ok(orderService.queryOrdersForRefresh(qi, qi.getLoginId(), headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/{travelDate}/{trainNumber}")
    public HttpEntity calculateSoldTicket(@PathVariable Date travelDate, @PathVariable String trainNumber,
                                          @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Calculate Sold Tickets] Date: {} TrainNumber: {}", travelDate, trainNumber);
        return ok(orderService.queryAlreadySoldOrders(travelDate, trainNumber, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/price/{orderId}")
    public HttpEntity getOrderPrice(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Get Order Price] Order Id: {}", orderId);
        // String
        return ok(orderService.getOrderPrice(orderId, headers));
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/orderPay/{orderId}")
    public HttpEntity payOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Pay Order] Order Id: {}", orderId);
        // Order
        return ok(orderService.payOrder(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/{orderId}")
    public HttpEntity getOrderById(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Get Order By Id] Order Id: {}", orderId);
        // Order
        return ok(orderService.getOrderById(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/status/{orderId}/{status}")
    public HttpEntity modifyOrder(@PathVariable String orderId, @PathVariable int status, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Modify Order Status] Order Id: {}", orderId);
        // Order
        return ok(orderService.modifyOrder(orderId, status, headers));
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order/security/{checkDate}/{accountId}")
    public HttpEntity securityInfoCheck(@PathVariable Date checkDate, @PathVariable String accountId,
                                        @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Security Info Get] {}", accountId);
        return ok(orderService.checkSecurityAboutOrder(checkDate, accountId, headers));
    }


    @CrossOrigin(origins = "*")
    @PutMapping(path = "/order")
    public HttpEntity saveOrderInfo(@RequestBody Order orderInfo,
                                    @RequestHeader HttpHeaders headers) {

        OrderController.LOGGER.info("[Verify Login] Success");
        return ok(orderService.saveChanges(orderInfo, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/order/admin")
    public HttpEntity updateOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        // Order
        OrderController.LOGGER.info("Update Order, OrderId: {}", order.getId());
        return ok(orderService.updateOrder(order, headers));
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/order/{orderId}")
    public HttpEntity deleteOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Delete Order] Order Id: {}", orderId);
        // Order
        return ok(orderService.deleteOrder(orderId, headers));
    }

    /***************For super admin(Single Service Test*******************/

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/order")
    public HttpEntity findAllOrder(@RequestHeader HttpHeaders headers) {
        OrderController.LOGGER.info("[Find All Order]");
        // ArrayList<Order>
        return ok(orderService.getAllOrders(headers));
    }

}
