package food.service;

import edu.fudan.common.util.Response;
import food.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface FoodMapService {

    // create data
    Response createFoodStore(FoodStore fs, HttpHeaders headers);

    TrainFood createTrainFood(TrainFood tf, HttpHeaders headers);

    // query all food
    Response listFoodStores(HttpHeaders headers);

    Response listTrainFood(HttpHeaders headers);

    // query according id
    Response listFoodStoresByStationId(String stationId, HttpHeaders headers);

    Response listTrainFoodByTripId(String tripId, HttpHeaders headers);

    Response getFoodStoresByStationIds(List<String> stationIds);
}
