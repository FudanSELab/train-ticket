package adminorder.service;

import adminorder.entity.*;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author fdse
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    @Override
    public Response getAllOrders(HttpHeaders headers) {

        AdminOrderServiceImpl.LOGGER.info("[Get All Orders]");
        //Get all of the orders
        ArrayList<Order> orders = new ArrayList<>();
        //From ts-order-service
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response<ArrayList<Order>>> re = restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                });
        Response<ArrayList<Order>> result = re.getBody();

        if (result.getStatus() == 1) {
            AdminOrderServiceImpl.LOGGER.info("[Get Orders From ts-order-service successfully!]");
            ArrayList<Order> orders1 = result.getData();
            orders.addAll(orders1);
        } else {
            AdminOrderServiceImpl.LOGGER.error("[Get Orders From ts-order-service fail!]");
        }
        //From ts-order-other-service
        HttpEntity requestEntity2 = new HttpEntity(null);
        ResponseEntity<Response<ArrayList<Order>>> re2 = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther",
                HttpMethod.GET,
                requestEntity2,
                new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                });
        result = re2.getBody();

        if (result.getStatus() == 1) {
            AdminOrderServiceImpl.LOGGER.info("[Get Orders From ts-order-other-service successfully!]");
            ArrayList<Order> orders1 = (ArrayList<Order>) result.getData();
            orders.addAll(orders1);
        } else {
            AdminOrderServiceImpl.LOGGER.error("[Get Orders From ts-order-other-service fail!]");
        }
        //Return orders
        return new Response<>(1, "Get the orders successfully!", orders);

    }

    @Override
    public Response deleteOrder(String orderId, String trainNumber, HttpHeaders headers) {
        Response deleteOrderResult;
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            AdminOrderServiceImpl.LOGGER.info("[Delete Order]");
            HttpEntity requestEntity = new HttpEntity(null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/" + orderId,
                    HttpMethod.DELETE,
                    requestEntity,
                    Response.class);
            deleteOrderResult = re.getBody();

        } else {
            AdminOrderServiceImpl.LOGGER.info("[Delete Order Other]");
            HttpEntity requestEntity = new HttpEntity(null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + orderId,
                    HttpMethod.DELETE,
                    requestEntity,
                    Response.class);
            deleteOrderResult = re.getBody();

        }
        return deleteOrderResult;

    }

    @Override
    public Response updateOrder(Order request, HttpHeaders headers) {

        Response updateOrderResult;
        LOGGER.info("UPDATE ORDER INFO : " + request.toString());
        if (request.getTrainNumber().startsWith("G") || request.getTrainNumber().startsWith("D")) {

            AdminOrderServiceImpl.LOGGER.info("[Update Order]");
            HttpEntity requestEntity = new HttpEntity(request, null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/admin",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
            updateOrderResult = re.getBody();

        } else {
            AdminOrderServiceImpl.LOGGER.info("[Add New Order Other]");
            HttpEntity requestEntity = new HttpEntity(request, null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/admin",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
            updateOrderResult = re.getBody();

        }
        return updateOrderResult;
    }

    @Override
    public Response addOrder(Order request, HttpHeaders headers) {

        Response addOrderResult;
        if (request.getTrainNumber().startsWith("G") || request.getTrainNumber().startsWith("D")) {
            AdminOrderServiceImpl.LOGGER.info("[Add New Order]");
            HttpEntity requestEntity = new HttpEntity(request, null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/admin",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            addOrderResult = re.getBody();

        } else {
            AdminOrderServiceImpl.LOGGER.info("[Add New Order Other]");
            HttpEntity requestEntity = new HttpEntity(request, null);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/admin",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            addOrderResult = re.getBody();

        }
        return addOrderResult;

    }


}
