package other.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import other.entity.*;
import other.service.OrderOtherService;

import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/orderOtherService")
public class OrderOtherController {

    @Autowired
    private OrderOtherService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderOtherController.class);

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Order Other Service ] !";
    }

    /***************************For Normal Use***************************/

    @PostMapping(value = "/orderOther/tickets")
    public HttpEntity getTicketListByDateAndTripId(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Get Sold Ticket] Date: {}", seatRequest.getTravelDate().toString());
        return ok(orderService.getSoldTickets(seatRequest, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/orderOther")
    public HttpEntity createNewOrder(@RequestBody Order createOrder, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Create Order] Create Order form {}  ---> {} at {}", createOrder.getFrom(), createOrder.getTo(), createOrder.getTravelDate());

        OrderOtherController.LOGGER.info("[Verify Login] Success");
        return ok(orderService.create(createOrder, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/orderOther/admin")
    public HttpEntity addcreateNewOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("Add new order, OrderId: {}", order.getId());
        return ok(orderService.addNewOrder(order, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/orderOther/query")
    public HttpEntity queryOrders(@RequestBody QueryInfo qi,
                                  @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Query Orders] Query Orders for {}", qi.getLoginId());
        return ok(orderService.queryOrders(qi, qi.getLoginId(), headers));

    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/orderOther/refresh")
    public HttpEntity queryOrdersForRefresh(@RequestBody QueryInfo qi,
                                            @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Query Orders] Query Orders for {}", qi.getLoginId());
        return ok(orderService.queryOrdersForRefresh(qi, qi.getLoginId(), headers));
    }


    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/{travelDate}/{trainNumber}")
    public HttpEntity calculateSoldTicket(@PathVariable Date travelDate, @PathVariable String trainNumber,
                                          @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Calculate Sold Tickets] Date: {} TrainNumber: {}", travelDate, trainNumber);
        return ok(orderService.queryAlreadySoldOrders(travelDate, trainNumber, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/price/{orderId}")
    public HttpEntity getOrderPrice(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Get Order Price] Order Id: {}", orderId);
        return ok(orderService.getOrderPrice(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/orderPay/{orderId}")
    public HttpEntity payOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Pay Order] Order Id: {}", orderId);
        return ok(orderService.payOrder(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/{orderId}")
    public HttpEntity getOrderById(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Get Order By Id] Order Id: {}", orderId);
        return ok(orderService.getOrderById(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/status/{orderId}/{status}")
    public HttpEntity modifyOrder(@PathVariable String orderId, @PathVariable int status, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Modify Order Status] Order Id: {}", orderId);
        return ok(orderService.modifyOrder(orderId, status, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther/security/{checkDate}/{accountId}")
    public HttpEntity securityInfoCheck(@PathVariable Date checkDate, @PathVariable String accountId,
                                        @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Security Info Get]");
        return ok(orderService.checkSecurityAboutOrder(checkDate, accountId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/orderOther")
    public HttpEntity saveOrderInfo(@RequestBody Order orderInfo,
                                    @RequestHeader HttpHeaders headers) {

        OrderOtherController.LOGGER.info("[Verify Login] Success");
        return ok(orderService.saveChanges(orderInfo, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/orderOther/admin")
    public HttpEntity updateOrder(@RequestBody Order order, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("Update Order, OrderId: {}", order.getId());
        return ok(orderService.updateOrder(order, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/orderOther/{orderId}")
    public HttpEntity deleteOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Delete Order] Order Id: {}", orderId);
        return ok(orderService.deleteOrder(orderId, headers));
    }

    /***************For super admin(Single Service Test*******************/

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/orderOther")
    public HttpEntity findAllOrder(@RequestHeader HttpHeaders headers) {
        OrderOtherController.LOGGER.info("[Find All Order]");
        return ok(orderService.getAllOrders(headers));
    }

}
