package food.service;

import edu.fudan.common.util.Response;
import food.entity.TrainFood;
import org.springframework.http.HttpHeaders;

public interface TrainFoodService {

    TrainFood createTrainFood(TrainFood tf, HttpHeaders headers);

    Response listTrainFood(HttpHeaders headers);

    Response listTrainFoodByTripId(String tripId, HttpHeaders headers);
}
