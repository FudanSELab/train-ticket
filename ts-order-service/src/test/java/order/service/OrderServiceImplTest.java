package order.service;

import edu.fudan.common.entity.OrderStatus;
import edu.fudan.common.util.Response;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    public void create_shouldReturnErrorWhenOrderExists() {
        Order order = new Order();
        order.setUserId("account-1");
        ArrayList<Order> existing = new ArrayList<>();
        existing.add(order);

        when(orderRepository.findByAccountId("account-1")).thenReturn(existing);

        Response<Order> response = orderService.create(order, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order already exist", response.getMsg());
    }

    @Test
    public void create_shouldPersistWhenOrderIsNew() {
        Order order = new Order();
        order.setUserId("account-1");

        when(orderRepository.findByAccountId("account-1")).thenReturn(new ArrayList<>());
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<Order> response = orderService.create(order, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success", response.getMsg());
        Assertions.assertNotNull(response.getData().getId());
        verify(orderRepository).save(Mockito.any(Order.class));
    }

    @Test
    public void update_shouldReturnErrorWhenOrderMissing() {
        Order order = new Order();
        order.setId("order-id");

        when(orderRepository.findById("order-id")).thenReturn(Optional.empty());

        Response<Order> response = orderService.update(order, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Not Found", response.getMsg());
    }

    @Test
    public void update_shouldPersistChangesWhenOrderExists() {
        Order updateRequest = new Order();
        updateRequest.setId("order-id");
        updateRequest.setUserId("account-2");
        updateRequest.setContactsName("Alice");
        updateRequest.setSeatNumber("12A");
        updateRequest.setFromStationId("shanghai");
        updateRequest.setToStationId("beijing");
        updateRequest.setPrice("99.9");

        Order existing = new Order();
        existing.setId("order-id");
        existing.setUserId("account-1");
        existing.setContactsName("Bob");

        when(orderRepository.findById("order-id")).thenReturn(Optional.of(existing));
        when(orderRepository.save(existing)).thenReturn(existing);

        Response<Order> response = orderService.update(updateRequest, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success", response.getMsg());
        Assertions.assertEquals("account-2", existing.getUserId());
        Assertions.assertEquals("Alice", existing.getContactsName());
        Assertions.assertEquals("12A", existing.getSeatNumber());
        Assertions.assertEquals("shanghai", existing.getFromStationId());
        Assertions.assertEquals("beijing", existing.getToStationId());
        Assertions.assertEquals("99.9", existing.getPrice());
    }

    @Test
    public void updateStatus_shouldReturnErrorWhenNotFound() {
        when(orderRepository.findById("order-id")).thenReturn(Optional.empty());

        Response<Order> response = orderService.updateStatus("order-id", 2, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Not Found", response.getMsg());
    }

    @Test
    public void updateStatus_shouldUpdateStatusWhenOrderExists() {
        Order order = new Order();
        order.setId("order-id");
        order.setStatus(0);

        when(orderRepository.findById("order-id")).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Response<Order> response = orderService.updateStatus("order-id", 2, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Modify Order Success", response.getMsg());
        Assertions.assertEquals(2, response.getData().getStatus());
    }

    @Test
    public void pay_shouldReturnErrorWhenOrderMissing() {
        when(orderRepository.findById("order-id")).thenReturn(Optional.empty());

        Response<Order> response = orderService.pay("order-id", headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Not Found", response.getMsg());
    }

    @Test
    public void pay_shouldSetStatusToPaid() {
        Order order = new Order();
        order.setId("order-id");
        order.setStatus(0);

        when(orderRepository.findById("order-id")).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Response<Order> response = orderService.pay("order-id", headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Pay Order Success.", response.getMsg());
        Assertions.assertEquals(OrderStatus.PAID.getCode(), response.getData().getStatus());
    }

    @Test
    public void delete_shouldReturnErrorWhenOrderNotFound() {
        String orderId = UUID.randomUUID().toString();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Response<Order> response = orderService.delete(orderId, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Not Exist.", response.getMsg());
    }

    @Test
    public void delete_shouldRemoveOrderWhenFound() {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Response<Order> response = orderService.delete(orderId, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Delete Order Success", response.getMsg());
        Assertions.assertSame(order, response.getData());
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    public void ensureOrder_shouldSkipWhenOrderAlreadyExists() {
        Order order = new Order();
        order.setId("order-id");

        when(orderRepository.existsById("order-id")).thenReturn(true);

        orderService.ensureOrder(order, headers);

        verify(orderRepository, never()).save(Mockito.any(Order.class));
    }

    @Test
    public void ensureOrder_shouldSaveWhenOrderDoesNotExist() {
        Order order = new Order();
        order.setId("order-id");

        when(orderRepository.existsById("order-id")).thenReturn(false);

        orderService.ensureOrder(order, headers);

        verify(orderRepository).save(order);
    }
}


