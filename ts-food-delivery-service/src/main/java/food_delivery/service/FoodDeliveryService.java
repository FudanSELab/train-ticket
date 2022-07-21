package food_delivery.service;

import edu.fudan.common.util.Response;
import food_delivery.entity.DeliveryInfo;
import food_delivery.entity.FoodDeliveryOrder;
import food_delivery.entity.SeatInfo;
import food_delivery.entity.TripInfo;
import org.springframework.http.HttpHeaders;

public interface FoodDeliveryService {

    Response createFoodDeliveryOrder(FoodDeliveryOrder fd, HttpHeaders headers);

    Response deleteFoodDeliveryOrder(String id, HttpHeaders headers);

    Response getFoodDeliveryOrderById(String id, HttpHeaders headers);

    Response getAllFoodDeliveryOrders(HttpHeaders headers);

    Response getFoodDeliveryOrderByStoreId(String storeId, HttpHeaders headers);

    Response updateTripId(TripInfo tripInfo, HttpHeaders headers);

    Response updateSeatNo(SeatInfo seatInfo, HttpHeaders headers);

    Response updateDeliveryTime(DeliveryInfo deliveryInfo, HttpHeaders headers);
}
