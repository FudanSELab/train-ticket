package foodsearch.service;

import edu.fudan.common.util.Response;
import foodsearch.entity.*;
import foodsearch.repository.FoodOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodServiceImpl.class);

    String success = "Success.";
    String orderIdNotExist = "Order Id Is Non-Existent.";

    @Override
    public Response createFoodOrder(FoodOrder addFoodOrder, HttpHeaders headers) {

        FoodOrder fo = foodOrderRepository.findByOrderId(addFoodOrder.getOrderId());
        if (fo != null) {
            FoodServiceImpl.LOGGER.info("[Food-Service][AddFoodOrder] Order Id Has Existed.");
            return new Response<>(0, "Order Id Has Existed.", null);
        } else {
            fo = new FoodOrder();
            fo.setId(UUID.randomUUID());
            fo.setOrderId(addFoodOrder.getOrderId());
            fo.setFoodType(addFoodOrder.getFoodType());
            if (addFoodOrder.getFoodType() == 2) {
                fo.setStationName(addFoodOrder.getStationName());
                fo.setStoreName(addFoodOrder.getStoreName());
            }
            fo.setFoodName(addFoodOrder.getFoodName());
            fo.setPrice(addFoodOrder.getPrice());
            foodOrderRepository.save(fo);
            FoodServiceImpl.LOGGER.info("[Food-Service][AddFoodOrder] Success.");
            return new Response<>(1, success, fo);
        }
    }

    @Override
    public Response deleteFoodOrder(String orderId, HttpHeaders headers) {
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (foodOrder == null) {
            FoodServiceImpl.LOGGER.info("[Food-Service][Cancel FoodOrder] Order Id Is Non-Existent.");
            return new Response<>(0, orderIdNotExist, null);
        } else {
            foodOrderRepository.deleteFoodOrderByOrderId(UUID.fromString(orderId));
            FoodServiceImpl.LOGGER.info("[Food-Service][Cancel FoodOrder] Success.");
            return new Response<>(1, success, null);
        }
    }

    @Override
    public Response findAllFoodOrder(HttpHeaders headers) {
        List<FoodOrder> foodOrders = foodOrderRepository.findAll();
        if (foodOrders != null && !foodOrders.isEmpty()) {
            return new Response<>(1, success, foodOrders);
        } else {
            return new Response<>(0, "No Content", null);
        }
    }


    @Override
    public Response updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findById(updateFoodOrder.getId());
        if (fo == null) {
            FoodServiceImpl.LOGGER.info("[Food-Service][Update FoodOrder] Order Id Is Non-Existent.");
            return new Response<>(0, orderIdNotExist, null);
        } else {
            fo.setFoodType(updateFoodOrder.getFoodType());
            if (updateFoodOrder.getFoodType() == 1) {
                fo.setStationName(updateFoodOrder.getStationName());
                fo.setStoreName(updateFoodOrder.getStoreName());
            }
            fo.setFoodName(updateFoodOrder.getFoodName());
            fo.setPrice(updateFoodOrder.getPrice());
            foodOrderRepository.save(fo);
            FoodServiceImpl.LOGGER.info("[Food-Service][Update FoodOrder] Success.");
            return new Response<>(1, "Success", fo);
        }
    }

    @Override
    public Response findByOrderId(String orderId, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (fo != null) {
            FoodServiceImpl.LOGGER.info("[Food-Service][Find Order by id] Success.");
            return new Response<>(1, success, fo);
        } else {
            FoodServiceImpl.LOGGER.info("[Food-Service][Find Order by id] Order Id Is Non-Existent.");
            return new Response<>(0, orderIdNotExist, null);
        }
    }


    @Override
    public Response getAllFood(String date, String startStation, String endStation, String tripId, HttpHeaders headers) {
        FoodServiceImpl.LOGGER.info("data={} start={} end={} tripid={}", date, startStation, endStation, tripId);
        AllTripFood allTripFood = new AllTripFood();

        if (null == tripId || tripId.length() <= 2) {
            return new Response<>(0, "Trip id is not suitable", null);
        }

        // need return this tow element
        List<TrainFood> trainFoodList = null;
        Map<String, List<FoodStore>> foodStoreListMap = new HashMap<>();

        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetTrainFoodListResult = new HttpEntity(headers);
        ResponseEntity<Response<List<TrainFood>>> reGetTrainFoodListResult = restTemplate.exchange(
                "http://ts-food-map-service:18855/api/v1/foodmapservice/trainfoods/" + tripId,
                HttpMethod.GET,
                requestEntityGetTrainFoodListResult,
                new ParameterizedTypeReference<Response<List<TrainFood>>>() {
                });

        List<TrainFood> trainFoodListResult = reGetTrainFoodListResult.getBody().getData();

        if (trainFoodListResult != null) {
            trainFoodList = trainFoodListResult;
            FoodServiceImpl.LOGGER.info("[Food Service]Get Train Food List!");
        } else {
            FoodServiceImpl.LOGGER.info("[Food Service]Get the Get Food Request Failed!");
            return new Response<>(0, "Get the Get Food Request Failed!", null);
        }
        //车次途经的车站
        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetRouteResult = new HttpEntity(null, headers);
        ResponseEntity<Response<Route>> reGetRouteResult = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/routes/" + tripId,
                HttpMethod.GET,
                requestEntityGetRouteResult,
                new ParameterizedTypeReference<Response<Route>>() {
                });
        Response<Route> stationResult = reGetRouteResult.getBody();

        if (stationResult.getStatus() == 1) {
            Route route = stationResult.getData();
            List<String> stations = route.getStations();
            //去除不经过的站，如果起点终点有的话
            if (null != startStation && !"".equals(startStation)) {
                /**--------------------------------------------------------------------------------------*/
                HttpEntity requestEntityStartStationId = new HttpEntity(headers);
                ResponseEntity<Response<String>> reStartStationId = restTemplate.exchange(
                        "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + startStation,
                        HttpMethod.GET,
                        requestEntityStartStationId,
                        new ParameterizedTypeReference<Response<String>>() {
                        });
                Response<String> startStationId = reStartStationId.getBody();

                for (int i = 0; i < stations.size(); i++) {
                    if (stations.get(i).equals(startStationId.getData())) {
                        break;
                    } else {
                        stations.remove(i);
                    }
                }
            }
            if (null != endStation && !"".equals(endStation)) {
                /**--------------------------------------------------------------------------------------*/
                HttpEntity requestEntityEndStationId = new HttpEntity(headers);
                ResponseEntity<Response<String>> reEndStationId = restTemplate.exchange(
                        "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + endStation,
                        HttpMethod.GET,
                        requestEntityEndStationId,
                        new ParameterizedTypeReference<Response<String>>() {
                        });
                Response endStationId = reEndStationId.getBody();

                for (int i = stations.size() - 1; i >= 0; i--) {
                    if (stations.get(i).equals(endStationId.getData())) {
                        break;
                    } else {
                        stations.remove(i);
                    }
                }
            }

            HttpEntity requestEntityFoodStoresListResult = new HttpEntity(stations, headers);
            ResponseEntity<Response<List<FoodStore>>> reFoodStoresListResult = restTemplate.exchange(
                    "http://ts-food-map-service:18855/api/v1/foodmapservice/foodstores",
                    HttpMethod.POST,
                    requestEntityFoodStoresListResult,
                    new ParameterizedTypeReference<Response<List<FoodStore>>>() {
                    });
            List<FoodStore> foodStoresListResult = reFoodStoresListResult.getBody().getData();
            if (foodStoresListResult != null && !foodStoresListResult.isEmpty()) {
                for (String stationId : stations) {
                    List<FoodStore> res = foodStoresListResult.stream()
                            .filter(foodStore -> (foodStore.getStationId().equals(stationId)))
                            .collect(Collectors.toList());
                    foodStoreListMap.put(stationId, res);
                }
            } else {
                return new Response<>(0, "Get All Food Failed", allTripFood);
            }
        } else {
            return new Response<>(0, "Get All Food Failed", allTripFood);
        }
        allTripFood.setTrainFoodList(trainFoodList);
        allTripFood.setFoodStoreListMap(foodStoreListMap);
        return new Response<>(1, "Get All Food Success", allTripFood);
    }
}
