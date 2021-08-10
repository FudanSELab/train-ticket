package execute.serivce;

import edu.fudan.common.util.Response;
import execute.entity.*;
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

/**
 * @author fdse
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {

    @Autowired
    private RestTemplate restTemplate;

    String orderStatusWrong = "Order Status Wrong";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteServiceImpl.class);

    @Override
    public Response ticketExecute(String orderId, HttpHeaders headers) {
        //1.Get order information

        headers = null;
        Response<Order> resultFromOrder = getOrderByIdFromOrder(orderId, headers);
        Order order;
        if (resultFromOrder.getStatus() == 1) {
            order =   resultFromOrder.getData();
            //2.Check if the order can come in
            if (order.getStatus() != OrderStatus.COLLECTED.getCode()) {
                LOGGER.error("ticket execute error: {}, orderId: {}", orderStatusWrong, orderId);
                return new Response<>(0, orderStatusWrong, null);
            }
            //3.Confirm inbound, request change order information

            Response resultExecute = executeOrder(orderId, OrderStatus.USED.getCode(), headers);
            if (resultExecute.getStatus() == 1) {
                return new Response<>(1, "Success.", null);
            } else {
                LOGGER.error("executeOrder error: {}, orderId: {}", resultExecute.getMsg(), orderId);
                return new Response<>(0, resultExecute.getMsg(), null);
            }
        } else {
            resultFromOrder = getOrderByIdFromOrderOther(orderId, headers);
            if (resultFromOrder.getStatus() == 1) {
                order =   resultFromOrder.getData();
                //2.Check if the order can come in
                if (order.getStatus() != OrderStatus.COLLECTED.getCode()) {
                    LOGGER.error("ticket execute error: {}, orderId: {}", orderStatusWrong, orderId);
                    return new Response<>(0, orderStatusWrong, null);
                }
                //3.Confirm inbound, request change order information

                Response resultExecute = executeOrderOther(orderId, OrderStatus.USED.getCode(), headers);
                if (resultExecute.getStatus() == 1) {
                    return new Response<>(1, "Success", null);
                } else {
                    LOGGER.error("executeOrderOther error: {}, orderId: {}", resultExecute.getMsg(), orderId);
                    return new Response<>(0, resultExecute.getMsg(), null);
                }
            } else {
                LOGGER.error("ticker execute error: {}, , orderId: {}", "Order Not Found", orderId);
                return new Response<>(0, "Order Not Found", null);
            }
        }
    }

    @Override
    public Response ticketCollect(String orderId, HttpHeaders headers) {
        //1.Get order information

        headers = null;
        Response<Order> resultFromOrder = getOrderByIdFromOrder(orderId, headers);
        Order order;
        if (resultFromOrder.getStatus() == 1) {
            order =  resultFromOrder.getData();
            //2.Check if the order can come in
            if (order.getStatus() != OrderStatus.PAID.getCode() && order.getStatus() != OrderStatus.CHANGE.getCode()) {
                LOGGER.error("ticket collect error: {}, orderId: {}", orderStatusWrong, orderId);
                return new Response<>(0, orderStatusWrong, null);
            }
            //3.Confirm inbound, request change order information

            Response resultExecute = executeOrder(orderId, OrderStatus.COLLECTED.getCode(), headers);
            if (resultExecute.getStatus() == 1) {
                return new Response<>(1, "Success", null);
            } else {
                LOGGER.error("ticket collect error: {}, orderId: {}", resultExecute.getMsg(), orderId);
                return new Response<>(0, resultExecute.getMsg(), null);
            }
        } else {
            resultFromOrder = getOrderByIdFromOrderOther(orderId, headers);
            if (resultFromOrder.getStatus() == 1) {
                order = (Order) resultFromOrder.getData();
                //2.Check if the order can come in
                if (order.getStatus() != OrderStatus.PAID.getCode() && order.getStatus() != OrderStatus.CHANGE.getCode()) {
                    LOGGER.error("ticket collect error: {}, orderId: {}", orderStatusWrong, orderId);
                    return new Response<>(0, orderStatusWrong, null);
                }
                //3.Confirm inbound, request change order information
                Response resultExecute = executeOrderOther(orderId, OrderStatus.COLLECTED.getCode(), headers);
                if (resultExecute.getStatus() == 1) {
                    return new Response<>(1, "Success.", null);
                } else {
                    LOGGER.error("ticket collect error: {}, orderId: {}", resultExecute.getMsg(), orderId);
                    return new Response<>(0, resultExecute.getMsg(), null);
                }
            } else {
                LOGGER.error("ticket collect error: {}, orderId: {}", "Order Not Found", orderId);
                return new Response<>(0, "Order Not Found", null);
            }
        }
    }


    private Response executeOrder(String orderId, int status, HttpHeaders headers) {
        ExecuteServiceImpl.LOGGER.info("[Execute Service][Execute Order] Executing....");
        headers = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/status/" + orderId + "/" + status,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        return re.getBody();
    }


    private Response executeOrderOther(String orderId, int status, HttpHeaders headers) {
        ExecuteServiceImpl.LOGGER.info("[Execute Service][Execute Order] Executing....");
        headers = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/status/" + orderId + "/" + status,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        return re.getBody();
    }

    private Response<Order> getOrderByIdFromOrder(String orderId, HttpHeaders headers) {
        ExecuteServiceImpl.LOGGER.info("[Execute Service][Get Order] Getting....");
        headers = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                });
        return re.getBody();
    }

    private Response<Order> getOrderByIdFromOrderOther(String orderId, HttpHeaders headers) {
        ExecuteServiceImpl.LOGGER.info("[Execute Service][Get Order] Getting....");
        headers = null;
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                });
        return re.getBody();
    }

}
