package order.service;

import edu.fudan.common.client.dto.order.OrderQueryDto;
import edu.fudan.common.client.dto.order.SoldOrderStatDto;
import edu.fudan.common.client.dto.order.TravelDateNumberDto;
import edu.fudan.common.entity.OrderSecurity;
import edu.fudan.common.util.Response;
import order.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author fdse
 */
public interface OrderService {
    Response<Order> getOrderById(String orderId , HttpHeaders headers);

    Response<ArrayList<Order>> queryOrders(OrderQueryDto qi, String accountId, HttpHeaders headers);

    Response<SoldOrderStatDto> queryAlreadySoldOrders(TravelDateNumberDto request, HttpHeaders headers);

    Response<ArrayList<Order>> getAllOrders(HttpHeaders headers);

    Response<String> getOrderPrice(String orderId, HttpHeaders headers);

    Response<OrderSecurity> checkSecurityAboutOrder(Date checkDate, String accountId, HttpHeaders headers);
}

