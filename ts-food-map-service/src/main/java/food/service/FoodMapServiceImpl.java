package food.service;

import edu.fudan.common.util.Response;
import food.entity.FoodStore;
import food.entity.TrainFood;
import food.repository.FoodStoreRepository;
import food.repository.TrainFoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FoodMapServiceImpl implements FoodMapService {

    @Autowired
    FoodStoreRepository foodStoreRepository;

    @Autowired
    TrainFoodRepository trainFoodRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodMapServiceImpl.class);

    String success = "Success";
    String noContent = "No content";

    @Override
    public Response createFoodStore(FoodStore fs, HttpHeaders headers) {
        FoodStore fsTemp = foodStoreRepository.findById(fs.getId());
        if (fsTemp != null) {
            FoodMapServiceImpl.LOGGER.info("[Food Map Service][Init FoodStore] Already Exists Id: {}", fs.getId());
            return new Response<>(0, "Already Exists Id", null);
        } else {
            foodStoreRepository.save(fs);
            return new Response<>(1, "Save Success", fs);
        }
    }

    @Override
    public TrainFood createTrainFood(TrainFood tf, HttpHeaders headers) {
        TrainFood tfTemp = trainFoodRepository.findById(tf.getId());
        if (tfTemp != null) {
            FoodMapServiceImpl.LOGGER.info("[Food Map Service][Init TrainFood] Already Exists Id: {}", tf.getId());
        } else {
            trainFoodRepository.save(tf);
        }
        return tf;
    }

    @Override
    public Response listFoodStores(HttpHeaders headers) {
        List<FoodStore> foodStores = foodStoreRepository.findAll();
        if (foodStores != null && !foodStores.isEmpty()) {
            return new Response<>(1, success, foodStores);
        } else {
            return new Response<>(0, "Foodstore is empty", null);
        }
    }

    @Override
    public Response listTrainFood(HttpHeaders headers) {
        List<TrainFood> trainFoodList = trainFoodRepository.findAll();
        if (trainFoodList != null && !trainFoodList.isEmpty()) {
            return new Response<>(1, success, trainFoodList);
        } else {
            return new Response<>(0, noContent, null);
        }
    }

    @Override
    public Response listFoodStoresByStationId(String stationId, HttpHeaders headers) {
        List<FoodStore> foodStoreList = foodStoreRepository.findByStationId(stationId);
        if (foodStoreList != null && !foodStoreList.isEmpty()) {
            return new Response<>(1, success, foodStoreList);
        } else {
            return new Response<>(0, "FoodStore is empty", null);
        }
    }

    @Override
    public Response listTrainFoodByTripId(String tripId, HttpHeaders headers) {
        List<TrainFood> trainFoodList = trainFoodRepository.findByTripId(tripId);
        if (trainFoodList != null) {
            return new Response<>(1, success, trainFoodList);
        } else {
            return new Response<>(0, noContent, null);
        }
    }

    @Override
    public Response getFoodStoresByStationIds(List<String> stationIds) {
        List<FoodStore> foodStoreList = foodStoreRepository.findByStationIdIn(stationIds);
        if (foodStoreList != null) {
            return new Response<>(1, success, foodStoreList);
        } else {
            return new Response<>(0, noContent, null);
        }
    }
}
