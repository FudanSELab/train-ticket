package foodsearch.controller;

import foodsearch.entity.*;
import foodsearch.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.ResponseEntity.ok;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/foodservice")
public class FoodController {
    private static Logger logger = Logger.getLogger("my.logger.SocketHandlerTest");

    @Autowired
    FoodService foodService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Food Service ] !";
    }

    // get
    @GetMapping(path = "/orders")
    public HttpEntity findAllFoodOrder(@RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Try to Find all FoodOrder!");
        return ok(foodService.findAllFoodOrder(headers));
    }

    // add
    @PostMapping(path = "/orders")
    public HttpEntity createFoodOrder(@RequestBody FoodOrder addFoodOrder, @RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Try to Create a FoodOrder!");
        return ok(foodService.createFoodOrder(addFoodOrder, headers));
    }

    // update
    @PutMapping(path = "/orders")
    public HttpEntity updateFoodOrder(@RequestBody FoodOrder updateFoodOrder, @RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Try to Update a FoodOrder!");
        return ok(foodService.updateFoodOrder(updateFoodOrder, headers));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "/orders/{orderId}")
    public HttpEntity deleteFoodOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Try to Cancel a FoodOrder!");
        return ok(foodService.deleteFoodOrder(orderId, headers));
    }

    @GetMapping(path = "/orders/{orderId}")
    public HttpEntity findFoodOrderByOrderId(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Try to Find all FoodOrder!");
        return ok(foodService.findByOrderId(orderId, headers));
    }

    // 里面依赖很多其它服务，没有改完全
    @GetMapping(path = "/foods/{date}/{startStation}/{endStation}/{tripId}")
    public HttpEntity getAllFood(@PathVariable String date, @PathVariable String startStation,
                                 @PathVariable String endStation, @PathVariable String tripId,
                                 @RequestHeader HttpHeaders headers) {
        logger.info("[Food Service]Get the Get Food Request!");
        return ok(foodService.getAllFood(date, startStation, endStation, tripId, headers));
    }
}
