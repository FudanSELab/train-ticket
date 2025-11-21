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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
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
    public void createFoodOrder_shouldReturnErrorWhenOrderIdExists() {
        FoodOrder request = buildFoodOrder();
        when(foodOrderRepository.findByOrderId(request.getOrderId())).thenReturn(new FoodOrder());

        Response<FoodOrder> response = foodOrderService.createFoodOrder(request, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Id Has Existed.", response.getMsg());
    }

    @Test
    public void createFoodOrder_shouldSaveWhenOrderIsNew() {
        FoodOrder request = buildFoodOrder();
        when(foodOrderRepository.findByOrderId(request.getOrderId())).thenReturn(null);
        when(foodOrderRepository.save(Mockito.any(FoodOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<FoodOrder> response = foodOrderService.createFoodOrder(request, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertNotNull(response.getData().getId());
        verify(foodOrderRepository).save(Mockito.any(FoodOrder.class));
    }

    @Test
    public void deleteFoodOrder_shouldReturnErrorWhenOrderMissing() {
        String orderId = UUID.randomUUID().toString();
        when(foodOrderRepository.findByOrderId(orderId)).thenReturn(null);

        Response<Void> response = foodOrderService.deleteFoodOrder(orderId, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Id Is Non-Existent.", response.getMsg());
    }

    @Test
    public void deleteFoodOrder_shouldRemoveOrderWhenExists() {
        String orderId = UUID.randomUUID().toString();
        FoodOrder existing = buildFoodOrder();
        existing.setOrderId(orderId);

        when(foodOrderRepository.findByOrderId(orderId)).thenReturn(existing);

        Response<Void> response = foodOrderService.deleteFoodOrder(orderId, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        verify(foodOrderRepository).deleteFoodOrderByOrderId(orderId);
    }

    @Test
    public void updateFoodOrder_shouldReturnErrorWhenOrderNotFound() {
        FoodOrder update = buildFoodOrder();
        update.setId(UUID.randomUUID().toString());
        when(foodOrderRepository.findById(update.getId())).thenReturn(Optional.empty());

        Response<FoodOrder> response = foodOrderService.updateFoodOrder(update, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order Id Is Non-Existent.", response.getMsg());
    }

    @Test
    public void updateFoodOrder_shouldPersistChangesWhenOrderExists() {
        FoodOrder existing = buildFoodOrder();
        existing.setId(UUID.randomUUID().toString());

        FoodOrder update = buildFoodOrder();
        update.setId(existing.getId());
        update.setFoodType(2);
        update.setStationName("station");
        update.setStoreName("store");
        update.setFoodName("New Food");
        update.setPrice(15.5);

        when(foodOrderRepository.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(foodOrderRepository.save(existing)).thenReturn(existing);

        Response<FoodOrder> response = foodOrderService.updateFoodOrder(update, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success", response.getMsg());
        Assertions.assertEquals("New Food", response.getData().getFoodName());
        Assertions.assertEquals(15.5, response.getData().getPrice(), 0.0);
        Assertions.assertEquals(2, response.getData().getFoodType());
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


