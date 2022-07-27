package food.service;

import edu.fudan.common.util.Response;
import food.entity.StationFoodStore;
import food.repository.StationFoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StationFoodServiceImpl implements StationFoodService {

    @Autowired
    StationFoodRepository stationFoodRepository;

//    @Autowired
//    TrainFoodRepository trainFoodRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StationFoodServiceImpl.class);

    String success = "Success";
    String noContent = "No content";

    @Override
    public Response createFoodStore(StationFoodStore fs, HttpHeaders headers) {
        StationFoodStore fsTemp = stationFoodRepository.findById(fs.getId()).orElse(null);
        if (fsTemp != null) {
            StationFoodServiceImpl.LOGGER.error("[Init FoodStore] Already Exists Id: {}", fs.getId());
            return new Response<>(0, "Already Exists Id", null);
        } else {
            try{
                stationFoodRepository.save(fs);
                return new Response<>(1, "Save Success", fs);
            }catch(Exception e){
                return new Response<>(0, "Save failed", e.getMessage());
            }
        }
    }

//    @Override
//    public TrainFood createTrainFood(TrainFood tf, HttpHeaders headers) {
//        TrainFood tfTemp = trainFoodRepository.findById(tf.getId());
//        if (tfTemp != null) {
//            StationFoodServiceImpl.LOGGER.error("[Init TrainFood] Already Exists Id: {}", tf.getId());
//        } else {
//            trainFoodRepository.save(tf);
//        }
//        return tf;
//    }

    @Override
    public Response listFoodStores(HttpHeaders headers) {
        List<StationFoodStore> stationFoodStores = stationFoodRepository.findAll();
        if (stationFoodStores != null && !stationFoodStores.isEmpty()) {
            return new Response<>(1, success, stationFoodStores);
        } else {
            StationFoodServiceImpl.LOGGER.error("List food stores error: {}", "Food store is empty");
            return new Response<>(0, "Food store is empty", null);
        }
    }

//    @Override
//    public Response listTrainFood(HttpHeaders headers) {
//        List<TrainFood> trainFoodList = trainFoodRepository.findAll();
//        if (trainFoodList != null && !trainFoodList.isEmpty()) {
//            return new Response<>(1, success, trainFoodList);
//        } else {
//            StationFoodServiceImpl.LOGGER.error("List train food error: {}", noContent);
//            return new Response<>(0, noContent, null);
//        }
//    }

    @Override
    public Response listFoodStoresByStationName(String stationName, HttpHeaders headers) {
        List<StationFoodStore> stationFoodStoreList = stationFoodRepository.findByStationName(stationName);
        if (stationFoodStoreList != null && !stationFoodStoreList.isEmpty()) {
            return new Response<>(1, success, stationFoodStoreList);
        } else {
            StationFoodServiceImpl.LOGGER.error("List food stores by station id error: {}, stationName: {}", "Food store is empty", stationName);
            return new Response<>(0, "Food store is empty", null);
        }
    }

//    @Override
//    public Response listTrainFoodByTripId(String tripId, HttpHeaders headers) {
//        List<TrainFood> trainFoodList = trainFoodRepository.findByTripId(tripId);
//        if (trainFoodList != null) {
//            return new Response<>(1, success, trainFoodList);
//        } else {
//            StationFoodServiceImpl.LOGGER.error("List train food by trip id error: {}, tripId: {}", noContent, tripId);
//            return new Response<>(0, noContent, null);
//        }
//    }

    @Override
    public Response getFoodStoresByStationNames(List<String> stationNames) {
        List<StationFoodStore> stationFoodStoreList = stationFoodRepository.findByStationNameIn(stationNames);
        if (stationFoodStoreList != null) {
            return new Response<>(1, success, stationFoodStoreList);
        } else {
            StationFoodServiceImpl.LOGGER.error("List food stores by station ids error: {}, stationName list: {}", "Food store is empty", stationNames);
            return new Response<>(0, noContent, null);
        }
    }

    @Override
    public Response getStaionFoodStoreById(String id) {
        StationFoodStore stationFoodStore = stationFoodRepository.findById(id).orElse(null);
        if (stationFoodStore == null) {
            LOGGER.error("no such staionFoodStoreId: {}", id);
            return new Response<>(0, noContent, null);
        } else {
            return new Response<>(1, success, stationFoodStore);
        }
    }
}
