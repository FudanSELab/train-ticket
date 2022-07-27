package execute.service;

import edu.fudan.common.util.Response;
import edu.fudan.common.entity.Order;
import execute.serivce.ExecuteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ExecuteServiceImplTest {

    @InjectMocks
    private ExecuteServiceImpl executeServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTicketExecute1() {
        //mock getOrderByIdFromOrder()
        Order order = new Order();
        order.setStatus(2);
        Response<Order> response = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re);
        //mock executeOrder()
        Response response2 = new Response(1, null, null);
        ResponseEntity<Response> re2 = new ResponseEntity<>(response2, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/status/" + "order_id" + "/" + 6,
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re2);
        Response result = executeServiceImpl.ticketExecute("order_id", headers);
        Assert.assertEquals(new Response<>(1, "Success.", null), result);
    }

    @Test
    public void testTicketExecute2() {
        //mock getOrderByIdFromOrder(
        Response<Order> response = new Response<>(0, null, null);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re);
        //mock getOrderByIdFromOrderOther()
        Order order = new Order();
        order.setStatus(2);
        Response<Order> response2 = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re2);
        //mock executeOrderOther()
        Response response3 = new Response(1, null, null);
        ResponseEntity<Response> re3 = new ResponseEntity<>(response3, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/status/" + "order_id" + "/" + 6,
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re3);
        Response result = executeServiceImpl.ticketExecute("order_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", null), result);
    }

    @Test
    public void testTicketCollect1() {
        //mock getOrderByIdFromOrder()
        Order order = new Order();
        order.setStatus(1);
        Response<Order> response = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re);
        //mock executeOrder()
        Response response2 = new Response(1, null, null);
        ResponseEntity<Response> re2 = new ResponseEntity<>(response2, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/status/" + "order_id" + "/" + 2,
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re2);
        Response result = executeServiceImpl.ticketCollect("order_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", null), result);
    }

    @Test
    public void testTicketCollect2() {
        //mock getOrderByIdFromOrder(
        Response<Order> response = new Response<>(0, null, null);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re);
        //mock getOrderByIdFromOrderOther()
        Order order = new Order();
        order.setStatus(1);
        Response<Order> response2 = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + "order_id",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                })).thenReturn(re2);
        //mock executeOrderOther()
        Response response3 = new Response(1, null, null);
        ResponseEntity<Response> re3 = new ResponseEntity<>(response3, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/status/" + "order_id" + "/" + 2,
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re3);
        Response result = executeServiceImpl.ticketCollect("order_id", headers);
        Assert.assertEquals(new Response<>(1, "Success.", null), result);
    }

}
