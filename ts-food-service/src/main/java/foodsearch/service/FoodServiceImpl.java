package foodsearch.service;

import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import foodsearch.entity.*;
import foodsearch.mq.RabbitSend;
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

    @Autowired
    private RabbitSend sender;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodServiceImpl.class);

    String success = "Success.";
    String orderIdNotExist = "Order Id Is Non-Existent.";

    @Override
    public Response createFoodOrder(FoodOrder addFoodOrder, HttpHeaders headers) {

        FoodOrder fo = foodOrderRepository.findByOrderId(addFoodOrder.getOrderId());
        if (fo != null) {
            FoodServiceImpl.LOGGER.error("[AddFoodOrder] Order Id Has Existed, OrderId: {}", addFoodOrder.getOrderId());
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
            FoodServiceImpl.LOGGER.info("[AddFoodOrder] Success.");

            Delivery delivery = new Delivery();
            delivery.setFoodName(addFoodOrder.getFoodName());
            delivery.setOrderId(addFoodOrder.getOrderId());
            delivery.setStationName(addFoodOrder.getStationName());
            delivery.setStoreName(addFoodOrder.getStoreName());

            String deliveryJson = JsonUtils.object2Json(delivery);
            LOGGER.info("[AddFoodOrder] delivery info [{}] send to mq", deliveryJson);
            try {
                sender.send(deliveryJson);
            } catch (Exception e) {
                LOGGER.error("[AddFoodOrder] send delivery info to mq error, exception is [{}]", e.toString());
            }

            return new Response<>(1, success, fo);
        }
    }

    @Override
    public Response deleteFoodOrder(String orderId, HttpHeaders headers) {
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (foodOrder == null) {
            FoodServiceImpl.LOGGER.error("[Cancel FoodOrder] Order Id Is Non-Existent, orderId: {}", orderId);
            return new Response<>(0, orderIdNotExist, null);
        } else {
            foodOrderRepository.deleteFoodOrderByOrderId(UUID.fromString(orderId));
            FoodServiceImpl.LOGGER.info("[Cancel FoodOrder] Success.");
            return new Response<>(1, success, null);
        }
    }

    @Override
    public Response findAllFoodOrder(HttpHeaders headers) {
        List<FoodOrder> foodOrders = foodOrderRepository.findAll();
        if (foodOrders != null && !foodOrders.isEmpty()) {
            return new Response<>(1, success, foodOrders);
        } else {
            FoodServiceImpl.LOGGER.error("Find all food order error: {}", "No Content");
            return new Response<>(0, "No Content", null);
        }
    }


    @Override
    public Response updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findById(updateFoodOrder.getId());
        if (fo == null) {
            FoodServiceImpl.LOGGER.info("[Update FoodOrder] Order Id Is Non-Existent, orderId: {}", updateFoodOrder.getOrderId());
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
            FoodServiceImpl.LOGGER.info("[Update FoodOrder] Success.");
            return new Response<>(1, "Success", fo);
        }
    }

    @Override
    public Response findByOrderId(String orderId, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (fo != null) {
            FoodServiceImpl.LOGGER.info("[Find Order by id] Success.");
            return new Response<>(1, success, fo);
        } else {
            FoodServiceImpl.LOGGER.info("[Find Order by id] Order Id Is Non-Existent, orderId: {}", orderId);
            return new Response<>(0, orderIdNotExist, null);
        }
    }


    @Override
    public Response getAllFood(String date, String startStation, String endStation, String tripId, HttpHeaders headers) {
        FoodServiceImpl.LOGGER.info("data={} start={} end={} tripid={}", date, startStation, endStation, tripId);
        AllTripFood allTripFood = new AllTripFood();

        if (null == tripId || tripId.length() <= 2) {
            FoodServiceImpl.LOGGER.error("Get the Get Food Request Failed! Trip id is not suitable, date: {}, tripId: {}", date, tripId);
            return new Response<>(0, "Trip id is not suitable", null);
        }

        // need return this tow element
        List<TrainFood> trainFoodList = null;
        Map<String, List<FoodStore>> foodStoreListMap = new HashMap<>();

        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetTrainFoodListResult = new HttpEntity(null);
        ResponseEntity<Response<List<TrainFood>>> reGetTrainFoodListResult = restTemplate.exchange(
                "http://ts-food-map-service:18855/api/v1/foodmapservice/trainfoods/" + tripId,
                HttpMethod.GET,
                requestEntityGetTrainFoodListResult,
                new ParameterizedTypeReference<Response<List<TrainFood>>>() {
                });

        List<TrainFood> trainFoodListResult = reGetTrainFoodListResult.getBody().getData();

        if (trainFoodListResult != null) {
            trainFoodList = trainFoodListResult;
            FoodServiceImpl.LOGGER.info("Get Train Food List!");
        } else {
            FoodServiceImpl.LOGGER.error("Get the Get Food Request Failed!, date: {}, tripId: {}", date, tripId);
            return new Response<>(0, "Get the Get Food Request Failed!", null);
        }
        //车次途经的车站
        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetRouteResult = new HttpEntity(null, null);
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
                HttpEntity requestEntityStartStationId = new HttpEntity(null);
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
                HttpEntity requestEntityEndStationId = new HttpEntity(null);
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

            HttpEntity requestEntityFoodStoresListResult = new HttpEntity(stations, null);
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
                FoodServiceImpl.LOGGER.error("Get the Get Food Request Failed! foodStoresListResult is null, date: {}, tripId: {}", date, tripId);
                return new Response<>(0, "Get All Food Failed", allTripFood);
            }
        } else {
            FoodServiceImpl.LOGGER.error("Get the Get Food Request Failed! station status error, date: {}, tripId: {}", date, tripId);
            return new Response<>(0, "Get All Food Failed", allTripFood);
        }
        allTripFood.setTrainFoodList(trainFoodList);
        allTripFood.setFoodStoreListMap(foodStoreListMap);
        return new Response<>(1, "Get All Food Success", allTripFood);
    }
}
