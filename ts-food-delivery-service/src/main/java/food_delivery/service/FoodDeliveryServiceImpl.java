package food_delivery.service;


import edu.fudan.common.util.Response;
import food_delivery.entity.*;
import edu.fudan.common.entity.*;
import food_delivery.repository.FoodDeliveryOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodDeliveryServiceImpl implements FoodDeliveryService {

    @Autowired
    FoodDeliveryOrderRepository foodDeliveryOrderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FoodDeliveryServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response createFoodDeliveryOrder(FoodDeliveryOrder fd, HttpHeaders headers) {
        String stationFoodStoreId = fd.getStationFoodStoreId();

        String staion_food_service_url = getServiceUrl("ts-station-food-service");
//        staion_food_service_url = "http://ts-station-food-service"; // 测试
        ResponseEntity<Response<StationFoodStoreInfo>> getStationFoodStore = restTemplate.exchange(
                staion_food_service_url + "/api/v1/stationfoodservice/stationfoodstores/bystoreid/" + stationFoodStoreId,
                HttpMethod.GET,
                new HttpEntity(headers),
                new ParameterizedTypeReference<Response<StationFoodStoreInfo>>() {
                });
        Response<StationFoodStoreInfo> result = getStationFoodStore.getBody();
        StationFoodStoreInfo stationFoodStoreInfo = result.getData();
        List<Food> storeFoodList = stationFoodStoreInfo.getFoodList();
        Map<String, Double> foodPrice = storeFoodList.stream()
                                                     .collect(Collectors.toMap(Food::getFoodName, Food::getPrice));
        List<Food> orderFoodList = fd.getFoodList();
        double deliveryFee = 0;
        for (Food food : orderFoodList) {
            Double fee = foodPrice.get(food.getFoodName());
            if (fee == null) {
                LOGGER.error("{}:{} have no such food: {}", stationFoodStoreId, stationFoodStoreInfo.getStoreName(), food.getFoodName());
                return new Response<>(0, "Food not in store", null);
            }
            deliveryFee += fee;
        }
        deliveryFee += stationFoodStoreInfo.getDeliveryFee();
        fd.setDeliveryFee(deliveryFee);
        FoodDeliveryOrder res = foodDeliveryOrderRepository.save(fd);
        return new Response<>(1, "Save success", res);
    }

    @Override
    public Response deleteFoodDeliveryOrder(String id, HttpHeaders headers) {
        FoodDeliveryOrder t = foodDeliveryOrderRepository.findById(id).orElse(null);
        if (t == null) {
            LOGGER.error("[deleteFoodDeliveryOrder] No such food delivery order id: {}", id);
            return new Response<>(0, "No such food delivery order id", id);
        } else {
            foodDeliveryOrderRepository.deleteById(id);
            LOGGER.info("[deleteFoodDeliveryOrder] Delete success, food delivery order id: {}", id);
            return new Response<>(1, "Delete success", null);
        }
    }

    @Override
    public Response getFoodDeliveryOrderById(String id, HttpHeaders headers) {
        FoodDeliveryOrder t = foodDeliveryOrderRepository.findById(id).orElse(null);
        if (t == null) {
            LOGGER.error("[deleteFoodDeliveryOrder] No such food delivery order id: {}", id);
            return new Response<>(0, "No such food delivery order id", id);
        } else {
            LOGGER.info("[getFoodDeliveryOrderById] Get success, food delivery order id: {}", id);
            return new Response<>(1, "Get success", t);
        }
    }

    @Override
    public Response getAllFoodDeliveryOrders(HttpHeaders headers) {
        List<FoodDeliveryOrder> foodDeliveryOrders = foodDeliveryOrderRepository.findAll();
        if (foodDeliveryOrders == null) {
            LOGGER.error("[getAllFoodDeliveryOrders] Food delivery orders query error");
            return new Response<>(0, "food delivery orders query error", null);
        } else {
            LOGGER.info("[getAllFoodDeliveryOrders] Get all food delivery orders success");
            return new Response<>(1, "Get success", foodDeliveryOrders);
        }
    }

    @Override
    public Response getFoodDeliveryOrderByStoreId(String storeId, HttpHeaders headers) {
        List<FoodDeliveryOrder> foodDeliveryOrders = foodDeliveryOrderRepository.findByStationFoodStoreId(storeId);
        if (foodDeliveryOrders == null) {
            LOGGER.error("[getAllFoodDeliveryOrders] Food delivery orders query error");
            return new Response<>(0, "food delivery orders query error", storeId);
        } else {
            LOGGER.info("[getAllFoodDeliveryOrders] Get food delivery orders by storeId {} success", storeId);
            return new Response<>(1, "Get success", foodDeliveryOrders);
        }
    }

    @Override
    public Response updateTripId(TripOrderInfo tripInfo, HttpHeaders headers) {
        String id = tripInfo.getOrderId();
        String tripId = tripInfo.getTripId();
        FoodDeliveryOrder t = foodDeliveryOrderRepository.findById(id).orElse(null);
        if (t == null) {
            LOGGER.error("[updateTripId] No such delivery order id: {}", id);
            return new Response<>(0, "No such delivery order id", id);
        } else {
            t.setTripId(tripId);
            foodDeliveryOrderRepository.save(t);
            LOGGER.info("[updateTripId] update tripId success. id:{}, tripId:{}", id, tripId);
            return new Response<>(1, "update tripId success", t);
        }
    }

    @Override
    public Response updateSeatNo(SeatInfo seatInfo, HttpHeaders headers) {
        String id = seatInfo.getOrderId();
        int seatNo = seatInfo.getSeatNo();
        FoodDeliveryOrder t = foodDeliveryOrderRepository.findById(id).orElse(null);
        if (t == null) {
            LOGGER.error("[updateSeatNo] No such delivery order id: {}", id);
            return new Response<>(0, "No such delivery order id", id);
        } else {
            t.setSeatNo(seatNo);
            foodDeliveryOrderRepository.save(t);
            LOGGER.info("[updateSeatNo] update seatNo success. id:{}, seatNo:{}", id, seatNo);
            return new Response<>(1, "update seatNo success", t);
        }
    }

    @Override
    public Response updateDeliveryTime(DeliveryInfo deliveryInfo, HttpHeaders headers) {
        String id = deliveryInfo.getOrderId();
        String deliveryTime = deliveryInfo.getDeliveryTime();
        FoodDeliveryOrder t = foodDeliveryOrderRepository.findById(id).orElse(null);
        if (t == null) {
            LOGGER.error("[updateDeliveryTime] No such delivery order id: {}", id);
            return new Response<>(0, "No such delivery order id", id);
        } else {
            t.setDeliveryTime(deliveryTime);
            foodDeliveryOrderRepository.save(t);
            LOGGER.info("[updateDeliveryTime] update deliveryTime success. id:{}, deliveryTime:{}", id, deliveryTime);
            return new Response<>(1, "update deliveryTime success", t);
        }
    }
}
