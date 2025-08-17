package adminorder.service;

import edu.fudan.common.util.Response;
import foodsearch.entity.FoodOrder;
import foodsearch.repository.FoodOrderRepository;
import foodsearch.service.FoodServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(JUnit4.class)
public class FoodServiceImplTest {

    @InjectMocks
    private FoodServiceImpl foodServiceImpl;

    @Mock
    private FoodOrderRepository foodOrderRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFoodOrder1() {
        FoodOrder fo = new FoodOrder();
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(fo);
        Response result = foodServiceImpl.createFoodOrder(fo, headers);
        Assert.assertEquals(new Response<>(0, "Order Id Has Existed.", null), result);
    }

    @Test
    public void testCreateFoodOrder2() {
        FoodOrder fo = new FoodOrder(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 2, "station_name", "store_name", "food_name", 3.0);
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(null);
        Mockito.when(foodOrderRepository.save(Mockito.any(FoodOrder.class))).thenReturn(null);
        Response result = foodServiceImpl.createFoodOrder(fo, headers);
        Assert.assertEquals("Success.", result.getMsg());
    }

    @Test
    public void testDeleteFoodOrder1() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(null);
        Response result = foodServiceImpl.deleteFoodOrder(orderId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "Order Id Is Non-Existent.", null), result);
    }

    @Test
    public void testDeleteFoodOrder2() {
        UUID orderId = UUID.randomUUID();
        FoodOrder foodOrder = new FoodOrder();
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(foodOrder);
        Mockito.doNothing().doThrow(new RuntimeException()).when(foodOrderRepository).deleteFoodOrderByOrderId(Mockito.any(UUID.class).toString());
        Response result = foodServiceImpl.deleteFoodOrder(orderId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success.", null), result);
    }

    @Test
    public void testFindAllFoodOrder1() {
        List<FoodOrder> foodOrders = new ArrayList<>();
        foodOrders.add(new FoodOrder());
        Mockito.when(foodOrderRepository.findAll()).thenReturn(foodOrders);
        Response result = foodServiceImpl.findAllFoodOrder(headers);
        Assert.assertEquals(new Response<>(1, "Success.", foodOrders), result);
    }

    @Test
    public void testFindAllFoodOrder2() {
        Mockito.when(foodOrderRepository.findAll()).thenReturn(null);
        Response result = foodServiceImpl.findAllFoodOrder(headers);
        Assert.assertEquals(new Response<>(0, "No Content", null), result);
    }

    @Test
    public void testUpdateFoodOrder1() {
        FoodOrder updateFoodOrder = new FoodOrder();
        Mockito.when(foodOrderRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(null);
        Response result = foodServiceImpl.updateFoodOrder(updateFoodOrder, headers);
        Assert.assertEquals(new Response<>(0, "Order Id Is Non-Existent.", null), result);
    }

    @Test
    public void testUpdateFoodOrder2() {
        FoodOrder updateFoodOrder = new FoodOrder(UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, "station_name", "store_name", "food_name", 3.0);
        Mockito.when(foodOrderRepository.findById(Mockito.any(UUID.class).toString())).thenReturn(Optional.of(updateFoodOrder));
        Mockito.when(foodOrderRepository.save(Mockito.any(FoodOrder.class))).thenReturn(null);
        Response result = foodServiceImpl.updateFoodOrder(updateFoodOrder, headers);
        Assert.assertEquals(new Response<>(1, "Success", updateFoodOrder), result);
    }

    @Test
    public void testFindByOrderId1() {
        UUID orderId = UUID.randomUUID();
        FoodOrder fo = new FoodOrder();
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(fo);
        Response result = foodServiceImpl.findByOrderId(orderId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Success.", fo), result);
    }

    @Test
    public void testFindByOrderId2() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(foodOrderRepository.findByOrderId(Mockito.any(UUID.class).toString())).thenReturn(null);
        Response result = foodServiceImpl.findByOrderId(orderId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "Order Id Is Non-Existent.", null), result);
    }

    @Test
    public void testGetAllFood() {

    }

}
