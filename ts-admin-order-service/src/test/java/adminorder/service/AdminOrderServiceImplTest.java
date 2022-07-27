package adminorder.service;

import edu.fudan.common.util.Response;
import edu.fudan.common.entity.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class AdminOrderServiceImplTest {

    @InjectMocks
    private AdminOrderServiceImpl adminOrderService;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders1() {
        Response<ArrayList<Order>> response = new Response<>(0, null, null);
        ResponseEntity<Response<ArrayList<Order>>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                })).thenReturn(re);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                })).thenReturn(re);
        Response result = adminOrderService.getAllOrders(headers);
        Assert.assertEquals(new Response<>(1, "Get the orders successfully!", new ArrayList<>()), result);
    }

    @Test
    public void testGetAllOrders2() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order());
        Response<ArrayList<Order>> response = new Response<>(1, null, orders);
        ResponseEntity<Response<ArrayList<Order>>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                })).thenReturn(re);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                })).thenReturn(re);
        Response result = adminOrderService.getAllOrders(headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testDeleteOrder1() {
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + "orderId",
                HttpMethod.DELETE,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.deleteOrder("orderId", "G", headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

    @Test
    public void testDeleteOrder2() {
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + "orderId",
                HttpMethod.DELETE,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.deleteOrder("orderId", "K", headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

    @Test
    public void testUpdateOrder1() {
        Order order = new Order(null, null, null, null, null, null, 0, null, "G", 0, 0, null, null, null, 0, null, null);
        HttpEntity<Order> requestEntity2 = new HttpEntity<>(order, headers);
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/admin",
                HttpMethod.PUT,
                requestEntity2,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.updateOrder(order, headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateOrder2() {
        Order order = new Order(null, null, null, null, null, null, 0, null, "K", 0, 0, null, null, null, 0, null, null);
        HttpEntity<Order> requestEntity2 = new HttpEntity<>(order, headers);
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/admin",
                HttpMethod.PUT,
                requestEntity2,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.updateOrder(order, headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAddOrder1() {
        Order order = new Order(null, null, null, null, null, null, 0, null, "G", 0, 0, null, null, null, 0, null,null);
        HttpEntity<Order> requestEntity2 = new HttpEntity<>(order, headers);
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/admin",
                HttpMethod.POST,
                requestEntity2,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.addOrder(order, headers);
        Assert.assertNotNull(result);
    }

    @Test
    public void testAddOrder2() {
        Order order = new Order(null, null, null, null, null, null, 0, null, "K", 0, 0, null, null, null, 0, null, null);
        HttpEntity<Order> requestEntity2 = new HttpEntity<>(order, headers);
        Response response = new Response();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/admin",
                HttpMethod.POST,
                requestEntity2,
                Response.class)).thenReturn(re);
        Response result = adminOrderService.addOrder(order, headers);
        Assert.assertNotNull(result);
    }
}
