package food.controller;

import food.service.FoodMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/foodmapservice")
public class FoodStoreController {

    @Autowired
    FoodMapService foodMapService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodStoreController.class);

    @GetMapping(path = "/foodstores/welcome")
    public String home() {
        return "Welcome to [ Food store Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores")
    public HttpEntity getAllFoodStores(@RequestHeader HttpHeaders headers) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get All FoodStores]");
        return ok(foodMapService.listFoodStores(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores/{stationId}")
    public HttpEntity getFoodStoresOfStation(@PathVariable String stationId, @RequestHeader HttpHeaders headers) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get FoodStores By StationId]");
        return ok(foodMapService.listFoodStoresByStationId(stationId, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/foodstores")
    public HttpEntity getFoodStoresByStationIds(@RequestBody List<String> stationIdList) {
        FoodStoreController.LOGGER.info("[Food Map Service][Get FoodStores By StationIds]");
        return ok(foodMapService.getFoodStoresByStationIds(stationIdList));
    }
}
