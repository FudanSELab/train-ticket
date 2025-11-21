package order.service.impl;

import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import order.repository.FoodOrderRepository;
import order.service.FoodOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    private static final String SUCCESS = "Success.";
    private static final String ORDER_ID_NOT_EXIST = "Order Id Is Non-Existent.";

    @Override
    public Response<List<FoodOrder>> findAllFoodOrder(HttpHeaders headers) {
        List<FoodOrder> foodOrders = foodOrderRepository.findAll();
        if (foodOrders != null && !foodOrders.isEmpty()) {
            return new Response<>(1, SUCCESS, foodOrders);
        } else {
            log.error("[findAllFoodOrder][Find all food order error: {}]", "No Content");
            return new Response<>(0, "No Content", null);
        }
    }

    @Override
    public Response<FoodOrder> findByOrderId(String orderId, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(orderId).toString());
        if (fo != null) {
            log.info("[findByOrderId][Find Order by id Success][orderId: {}]", orderId);
            return new Response<>(1, SUCCESS, fo);
        } else {
            log.warn("[findByOrderId][Find Order by id][Order Id Is Non-Existent][orderId: {}]", orderId);
            return new Response<>(0, ORDER_ID_NOT_EXIST, null);
        }
    }
}
