package order.service;

import order.entity.Order;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface AdminOrderService {
    Response getAllOrders(HttpHeaders headers);
    Response addOrder(Order request, HttpHeaders headers);
    Response updateOrder(Order request, HttpHeaders headers);
    Response deleteOrder(String orderId, String trainNumber, HttpHeaders headers);
}
