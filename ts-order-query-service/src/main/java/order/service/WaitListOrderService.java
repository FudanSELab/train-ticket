package order.service;

import edu.fudan.common.util.Response;
import order.entity.WaitListOrder;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface WaitListOrderService {
    /**
     * Get all orders, including completed and expired ones
     */
    Response<List<WaitListOrder>> getAllWaitListOrders(HttpHeaders headers);

    /**
     * Get all orders in the wait list
     */
    Response<List<WaitListOrder>> getAllActiveWaitListOrders(HttpHeaders headers);
}
