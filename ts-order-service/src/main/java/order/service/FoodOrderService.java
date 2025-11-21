package order.service;

import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import org.springframework.http.HttpHeaders;

import java.util.List;


public interface FoodOrderService {

    Response<FoodOrder> createFoodOrder(FoodOrder afoi, HttpHeaders headers);

    Response<Void> createFoodOrdersInBatch(List<FoodOrder> orders, HttpHeaders headers);
    
    Response<Void> deleteFoodOrder(String orderId, HttpHeaders headers);

    Response<FoodOrder> updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers);
}
