package order.service;

import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import org.springframework.http.HttpHeaders;

import java.util.List;


public interface FoodOrderService {
    Response<FoodOrder> findByOrderId(String orderId, HttpHeaders headers);

    Response<List<FoodOrder>> findAllFoodOrder(HttpHeaders headers);
}
