package food.controller;

import food.service.StationFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/stationfoodservice")
public class StationFoodController {

    @Autowired
    StationFoodService stationFoodService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StationFoodController.class);

    @GetMapping(path = "/stationfoodstores/welcome")
    public String home() {
        return "Welcome to [ Food store Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/stationfoodstores")
    public HttpEntity getAllFoodStores(@RequestHeader HttpHeaders headers) {
        StationFoodController.LOGGER.info("[Food Map Service][Get All FoodStores]");
        return ok(stationFoodService.listFoodStores(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/stationfoodstores/{stationId}")
    public HttpEntity getFoodStoresOfStation(@PathVariable String stationId, @RequestHeader HttpHeaders headers) {
        StationFoodController.LOGGER.info("[Food Map Service][Get FoodStores By StationId]");
        return ok(stationFoodService.listFoodStoresByStationId(stationId, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/stationfoodstores")
    public HttpEntity getFoodStoresByStationIds(@RequestBody List<String> stationIdList) {
        StationFoodController.LOGGER.info("[Food Map Service][Get FoodStores By StationIds]");
        return ok(stationFoodService.getFoodStoresByStationIds(stationIdList));
    }
    @GetMapping("/stationfoodstores/g/{stationFoodStoreId}")
    public HttpEntity getFoodListByStationFoodStoreId(@PathVariable String stationFoodStoreId, @RequestHeader HttpHeaders headers) {
        StationFoodController.LOGGER.info("[Food Map Service][Get Foodlist By stationFoodStoreId]");
        return ok(stationFoodService.getStaionFoodStoreById(stationFoodStoreId));
    }
}
