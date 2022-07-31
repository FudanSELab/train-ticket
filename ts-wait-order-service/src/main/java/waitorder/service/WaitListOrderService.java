package waitorder.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import waitorder.entity.WaitListOrder;
import waitorder.entity.WaitListOrderVO;

public interface WaitListOrderService {

    Response findOrderById(String id, HttpHeaders headers);

    Response create(WaitListOrderVO newOrder, HttpHeaders headers);

    /**
     * Get all orders, including completed and expired ones
     */
    Response getAllOrders(HttpHeaders headers);

    Response updateOrder(WaitListOrder order, HttpHeaders headers);

    /**
     * Use when modifying order status
     */
    Response modifyWaitListOrderStatus(int status, String orderId);

    /**
     * Get all orders in the wait list
     */
    Response getAllWaitListOrders(HttpHeaders headers);
}
