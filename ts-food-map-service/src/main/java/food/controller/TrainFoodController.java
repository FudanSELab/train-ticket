package food.controller;


import food.service.FoodMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.ResponseEntity.ok;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/foodmapservice")
public class TrainFoodController {
	
	private static final Logger log = Logger.getLogger("test");
	
    @Autowired
    FoodMapService foodMapService;

    @GetMapping(path = "/trainfoods/welcome")
    public String home() {
        return "Welcome to [ Train Food Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/trainfoods")
    public HttpEntity getAllTrainFood(@RequestHeader HttpHeaders headers) {
		log.info("[Food Map Service][Get All TrainFoods]");
        return ok(foodMapService.listTrainFood(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/trainfoods/{tripId}")
    public HttpEntity getTrainFoodOfTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
		log.info("[Food Map Service][Get TrainFoods By TripId]");
        return ok(foodMapService.listTrainFoodByTripId(tripId, headers));
    }
}
