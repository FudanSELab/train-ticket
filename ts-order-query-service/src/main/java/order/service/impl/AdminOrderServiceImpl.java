package order.service.impl;

import order.entity.Order;
import edu.fudan.common.util.Response;
import order.service.AdminOrderService;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private OrderService orderService;

    @Override
    public Response getAllOrders(HttpHeaders headers) {
        return orderService.getAllOrders(headers);
    }

    @Override
    public Response addOrder(Order request, HttpHeaders headers) {
        return orderService.addNewOrder(request, headers);
    }

    @Override
    public Response updateOrder(Order request, HttpHeaders headers) {
        return orderService.updateOrder(request, headers);
    }

    @Override
    public Response deleteOrder(String orderId, String trainNumber, HttpHeaders headers) {
        // trainNumber parameter not needed for deletion after merge
        return orderService.deleteOrder(orderId, headers);
    }
}
