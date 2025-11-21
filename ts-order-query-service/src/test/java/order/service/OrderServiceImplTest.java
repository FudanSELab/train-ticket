package order.service;

import edu.fudan.common.client.dto.order.OrderQueryDto;
import edu.fudan.common.client.dto.order.SoldOrderStatDto;
import edu.fudan.common.client.dto.order.TravelDateNumberDto;
import edu.fudan.common.entity.OrderSecurity;
import edu.fudan.common.entity.OrderStatus;
import edu.fudan.common.entity.SeatClass;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import order.entity.Order;
import order.repository.OrderRepository;
import order.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    private HttpHeaders headers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        headers = new HttpHeaders();
    }

    @Test
    public void queryOrders_shouldReturnAllOrdersWhenNoFilterEnabled() {
        OrderQueryDto query = new OrderQueryDto();
        query.setLoginId("user-1");

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(buildOrder("1", "user-1", OrderStatus.PAID.getCode()));

        when(orderRepository.findByUserId("user-1")).thenReturn(orders);

        Response<ArrayList<Order>> response = orderService.queryOrders(query, "user-1", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Get order num", response.getMsg());
        Assertions.assertEquals(orders, response.getData());
    }

    @Test
    public void queryOrders_shouldFilterByStateWhenEnabled() {
        OrderQueryDto query = new OrderQueryDto();
        query.setLoginId("user-1");
        query.enableStateQuery(OrderStatus.PAID.getCode());

        Order paidOrder = buildOrder("1", "user-1", OrderStatus.PAID.getCode());
        Order notPaidOrder = buildOrder("2", "user-1", OrderStatus.NOTPAID.getCode());
        ArrayList<Order> orders = new ArrayList<>(Arrays.asList(paidOrder, notPaidOrder));

        when(orderRepository.findByUserId("user-1")).thenReturn(orders);

        Response<ArrayList<Order>> response = orderService.queryOrders(query, "user-1", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals(1, response.getData().size());
        Assertions.assertEquals("1", response.getData().get(0).getId());
    }

    @Test
    public void queryAlreadySoldOrders_shouldAggregateSeatCounts() {
        Date travelDate = new Date();
        String travelDateStr = StringUtils.Date2String(travelDate);

        TravelDateNumberDto request = new TravelDateNumberDto();
        request.setTravelDate(travelDateStr);
        request.setTravelNumber("G1234");

        Order businessSeat = buildOrder("1", "user-1", OrderStatus.PAID.getCode());
        businessSeat.setSeatClass(SeatClass.BUSINESS.getCode());
        businessSeat.setTrainNumber("G1234");

        Order firstClassSeat = buildOrder("2", "user-2", OrderStatus.NOTPAID.getCode());
        firstClassSeat.setSeatClass(SeatClass.FIRSTCLASS.getCode());
        firstClassSeat.setTrainNumber("G1234");

        Order ignoredOrder = buildOrder("3", "user-3", OrderStatus.CHANGE.getCode());
        ignoredOrder.setSeatClass(SeatClass.SECONDCLASS.getCode());
        ignoredOrder.setTrainNumber("G1234");

        when(orderRepository.findByTravelDateAndTrainNumber(travelDateStr, "G1234"))
                .thenReturn(new ArrayList<>(Arrays.asList(businessSeat, firstClassSeat, ignoredOrder)));

        Response<SoldOrderStatDto> response = orderService.queryAlreadySoldOrders(request, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals(1, response.getData().getBusinessSeat());
        Assertions.assertEquals(1, response.getData().getFirstClassSeat());
        Assertions.assertEquals(0, response.getData().getSecondClassSeat());
    }

    @Test
    public void getAllOrders_shouldReturnNoContentWhenRepositoryEmpty() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        Response<ArrayList<Order>> response = orderService.getAllOrders(headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void getOrderPrice_shouldReturnPriceWhenOrderExists() {
        Order order = buildOrder("order-id", "user-1", OrderStatus.PAID.getCode());
        order.setPrice("88.0");

        when(orderRepository.findById("order-id")).thenReturn(Optional.of(order));

        Response<String> response = orderService.getOrderPrice("order-id", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("88.0", response.getData());
    }

    @Test
    public void getOrderPrice_shouldReturnErrorWhenOrderMissing() {
        when(orderRepository.findById("order-id")).thenReturn(Optional.empty());

        Response<String> response = orderService.getOrderPrice("order-id", headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("-1.0", response.getData());
    }

    @Test
    public void getOrderById_shouldReturnOrderWhenPresent() {
        Order order = buildOrder("order-id", "user-1", OrderStatus.PAID.getCode());
        when(orderRepository.findById("order-id")).thenReturn(Optional.of(order));

        Response<Order> response = orderService.getOrderById("order-id", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals(order, response.getData());
    }

    @Test
    public void getOrderById_shouldReturnErrorWhenMissing() {
        when(orderRepository.findById("order-id")).thenReturn(Optional.empty());

        Response<Order> response = orderService.getOrderById("order-id", headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertNull(response.getData());
    }

    @Test
    public void checkSecurityAboutOrder_shouldCalculateCounts() {
        Date now = new Date();
        Date withinOneHour = new Date(now.getTime() - 30 * 60 * 1000);
        Date older = new Date(now.getTime() - 2 * 60 * 60 * 1000);

        Order recentValid = buildOrder("1", "user-1", OrderStatus.NOTPAID.getCode());
        recentValid.setBoughtDate(StringUtils.Date2String(withinOneHour));

        Order oldValid = buildOrder("2", "user-1", OrderStatus.PAID.getCode());
        oldValid.setBoughtDate(StringUtils.Date2String(older));

        Order recentCancelled = buildOrder("3", "user-1", OrderStatus.CANCEL.getCode());
        recentCancelled.setBoughtDate(StringUtils.Date2String(older));

        when(orderRepository.findByUserId("user-1"))
                .thenReturn(new ArrayList<>(Arrays.asList(recentValid, oldValid, recentCancelled)));

        Response<OrderSecurity> response = orderService.checkSecurityAboutOrder(now, "user-1", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals(2, response.getData().getOrderNumOfValidOrder());
        Assertions.assertEquals(1, response.getData().getOrderNumInLastOneHour());
    }

    private Order buildOrder(String id, String userId, int status) {
        Order order = new Order();
        order.setId(id);
        order.setUserId(userId);
        order.setStatus(status);
        order.setSeatClass(SeatClass.BUSINESS.getCode());
        order.setTrainNumber("G1234");
        order.setBoughtDate(StringUtils.Date2String(new Date()));
        order.setTravelDate(StringUtils.Date2String(new Date()));
        order.setPrice("0.0");
        return order;
    }
}

