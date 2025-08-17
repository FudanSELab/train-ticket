package food.service;

import edu.fudan.common.util.Response;
import food.entity.StationFoodStore;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface StationFoodService {

    Response createFoodStore(StationFoodStore fs, HttpHeaders headers);

    Response listFoodStores(HttpHeaders headers);

    Response listFoodStoresByStationName(String stationName, HttpHeaders headers);

    Response getStaionFoodStoreById(String id);

    Response getFoodStoresByStationNames(List<String> stationNames);
}
