package order.service;

import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import order.repository.FoodOrderRepository;
import order.service.impl.FoodOrderServiceImpl;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FoodServiceImplTest {

    @InjectMocks
    private FoodOrderServiceImpl foodOrderService;

    @Mock
    private FoodOrderRepository foodOrderRepository;

    private HttpHeaders headers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        headers = new HttpHeaders();
    }

    @Test
    public void findAllFoodOrder_shouldReturnNoContentWhenEmpty() {
        when(foodOrderRepository.findAll()).thenReturn(Collections.emptyList());

        Response<List<FoodOrder>> response = foodOrderService.findAllFoodOrder(headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("No Content", response.getMsg());
    }

    @Test
    public void findAllFoodOrder_shouldReturnOrders() {
        List<FoodOrder> orders = new ArrayList<>();
        orders.add(buildFoodOrder());
        when(foodOrderRepository.findAll()).thenReturn(orders);

        Response<List<FoodOrder>> response = foodOrderService.findAllFoodOrder(headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertEquals(1, response.getData().size());
    }

    @Test
    public void findByOrderId_shouldReturnOrderWhenExists() {
        String orderId = UUID.randomUUID().toString();
        FoodOrder order = buildFoodOrder();
        order.setOrderId(orderId);
        when(foodOrderRepository.findByOrderId(orderId)).thenReturn(order);

        Response<FoodOrder> response = foodOrderService.findByOrderId(orderId, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertEquals(order, response.getData());
    }

    @Test
    public void findByOrderId_shouldReturnErrorWhenMissing() {
        String orderId = UUID.randomUUID().toString();
        when(foodOrderRepository.findByOrderId(orderId)).thenReturn(null);

        Response<FoodOrder> response = foodOrderService.findByOrderId(orderId, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Id Is Non-Existent.", response.getMsg());
    }

    private FoodOrder buildFoodOrder() {
        FoodOrder order = new FoodOrder();
        order.setId(UUID.randomUUID().toString());
        order.setOrderId(UUID.randomUUID().toString());
        order.setFoodType(1);
        order.setFoodName("Food");
        order.setPrice(10.0);
        return order;
    }
}

