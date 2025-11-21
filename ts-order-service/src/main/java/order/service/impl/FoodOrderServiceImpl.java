package order.service.impl;

import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import order.repository.FoodOrderRepository;
import order.service.FoodOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    private static final String SUCCESS = "Success.";
    private static final String UPDATE_SUCCESS = "Success";
    private static final String ORDER_ID_NOT_EXIST = "Order Id Is Non-Existent.";

    @Override
    public Response<Void> createFoodOrdersInBatch(List<FoodOrder> orders, HttpHeaders headers) {
        if (orders == null || orders.isEmpty()) {
            return new Response<>(1, SUCCESS, null);
        }

        Optional<String> duplicatedOrderId = orders.stream()
                .map(FoodOrder::getOrderId)
                .filter(this::orderExists)
                .findFirst();

        if (duplicatedOrderId.isPresent()) {
            String errorOrderId = duplicatedOrderId.get();
            log.error("[createFoodOrdersInBatch][AddFoodOrder][Order Id Has Existed][OrderId: {}]", errorOrderId);
            return new Response<>(0, "Order Id " + errorOrderId + "Existed", null);
        }

        // 批量发送消息
        // for(String deliveryJson: deliveryJsons) {
        //     log.info("[createFoodOrdersInBatch][AddFoodOrder][delivery info send to mq][delivery info: {}]", deliveryJson);
        //     try {
        //         sender.send(deliveryJson);
        //     } catch (Exception e) {
        //         log.error("[createFoodOrdersInBatch][AddFoodOrder][send delivery info to mq error][exception: {}]", e.toString());
        //     }
        // }
        // List<String> deliveryPayloads = new ArrayList<>();
        // orders.stream()
        //         .map(this::buildNewFoodOrder)
        //         .forEach(order -> {
        //             foodOrderRepository.save(order);
        //             log.info("[createFoodOrdersInBatch][AddFoodOrderBatch][Success Save One Order][FoodOrderId: {}]", order.getOrderId());
        //             deliveryPayloads.add(buildDeliveryJson(order));
        //         });

        return new Response<>(1, SUCCESS, null);
    }

    @Override
    public Response<FoodOrder> createFoodOrder(FoodOrder addFoodOrder, HttpHeaders headers) {

        FoodOrder fo = foodOrderRepository.findByOrderId(addFoodOrder.getOrderId());
        if (fo != null) {
            log.error("[createFoodOrder][AddFoodOrder][Order Id Has Existed][OrderId: {}]", addFoodOrder.getOrderId());
            return new Response<>(0, "Order Id Has Existed.", null);
        } else {
            fo = foodOrderRepository.save(buildNewFoodOrder(addFoodOrder));
            log.info("[createFoodOrder][AddFoodOrder Success]");
            return new Response<>(1, SUCCESS, fo);
        }
    }

    @Transactional
    @Override
    public Response<Void> deleteFoodOrder(String orderId, HttpHeaders headers) {
        String normalizedOrderId = UUID.fromString(orderId).toString();
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(normalizedOrderId);
        if (foodOrder == null) {
            log.error("[deleteFoodOrder][Cancel FoodOrder][Order Id Is Non-Existent][orderId: {}]", orderId);
            return new Response<>(0, ORDER_ID_NOT_EXIST, null);
        }
        foodOrderRepository.deleteFoodOrderByOrderId(orderId);
        log.info("[deleteFoodOrder][Cancel FoodOrder Success]");
        return new Response<>(1, SUCCESS, null);
    }

    @Override
    public Response<FoodOrder> updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers) {
        return foodOrderRepository.findById(updateFoodOrder.getId())
                .map(existing -> {
                    applyUpdates(existing, updateFoodOrder);
                    foodOrderRepository.save(existing);
                    log.info("[updateFoodOrder][Update FoodOrder Success]");
                    return new Response<>(1, UPDATE_SUCCESS, existing);
                })
                .orElseGet(() -> {
                    log.info("[updateFoodOrder][Update FoodOrder][Order Id Is Non-Existent][orderId: {}]", updateFoodOrder.getOrderId());
                    return new Response<>(0, ORDER_ID_NOT_EXIST, null);
                });
    }

    private boolean orderExists(String orderId) {
        return foodOrderRepository.findByOrderId(orderId) != null;
    }

    private FoodOrder buildNewFoodOrder(FoodOrder source) {
        FoodOrder target = new FoodOrder();
        target.setId(UUID.randomUUID().toString());
        target.setOrderId(source.getOrderId());
        target.setFoodType(source.getFoodType());
        if (source.getFoodType() == 2) {
            target.setStationName(source.getStationName());
            target.setStoreName(source.getStoreName());
        }
        target.setFoodName(source.getFoodName());
        target.setPrice(source.getPrice());
        return target;
    }

    private void applyUpdates(FoodOrder target, FoodOrder source) {
        target.setFoodType(source.getFoodType());
        if (source.getFoodType() == 1) {
            target.setStationName(source.getStationName());
            target.setStoreName(source.getStoreName());
        }
        target.setFoodName(source.getFoodName());
        target.setPrice(source.getPrice());
    }
}
