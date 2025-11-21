package order.service;

import edu.fudan.common.util.Response;
import order.entity.Order;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface OrderService {
    Response<Order> create(Order newOrder, HttpHeaders headers);

    Response<Order> delete(String orderId, HttpHeaders headers);

    Response<Order> pay(String orderId, HttpHeaders headers);

    Response<Order> updateStatus(String orderId, int status, HttpHeaders headers);

    Response<Order> update(Order order, HttpHeaders headers);

    void ensureOrder(Order order, HttpHeaders headers);
}
