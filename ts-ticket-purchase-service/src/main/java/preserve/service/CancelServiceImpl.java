package preserve.service;

import edu.fudan.common.entity.NotifyInfo;
import edu.fudan.common.entity.OrderStatus;
import edu.fudan.common.entity.Order;
import edu.fudan.common.entity.SeatClass;
import edu.fudan.common.entity.User;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of CancelService migrated from ts-cancel-service.
 */
@Service
public class CancelServiceImpl implements CancelService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelServiceImpl.class);

    private static final String ORDER_STATUS_CANCEL_NOT_PERMITTED = "Order Status Cancel Not Permitted";

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response cancelOrder(String orderId, String loginId, HttpHeaders headers) {
        Response<Order> orderResult = getOrderByIdFromOrder(orderId, headers);
        if (orderResult.getStatus() == 1) {
            LOGGER.info("[cancelOrder][Order found G|H]");
            Order order = orderResult.getData();
            if (order.getStatus() == OrderStatus.NOTPAID.getCode()
                    || order.getStatus() == OrderStatus.PAID.getCode() || order.getStatus() == OrderStatus.CHANGE.getCode()) {
                Response changeOrderResult = cancelFromOrder(order, headers);
                if (changeOrderResult.getStatus() == 1) {
                    LOGGER.info("[cancelOrder][Cancel Order Success]");
                    // Draw back money
                    String money = calculateRefund(order);
                    boolean status = drawbackMoney(money, loginId, headers);
                    if (!status) {
                        LOGGER.error("[cancelOrder][Draw Back Money Failed][loginId: {}, orderId: {}]", loginId, orderId);
                    }
                    return new Response<>(1, "Success.", "test not null");
                } else {
                    LOGGER.error("[cancelOrder][Cancel Order Failed][orderId: {}, Reason: {}]", orderId, changeOrderResult.getMsg());
                    return new Response<>(0, changeOrderResult.getMsg(), null);
                }
            } else {
                LOGGER.info("[cancelOrder][Order Status Not Permitted][loginId: {}, orderId: {}]", loginId, orderId);
                return new Response<>(0, ORDER_STATUS_CANCEL_NOT_PERMITTED, null);
            }
        } else {
            Response<Order> orderOtherResult = getOrderByIdFromOrderOther(orderId, headers);
            if (orderOtherResult.getStatus() == 1) {
                LOGGER.info("[cancelOrder][Order found Z|K|Other]");
                Order order = orderOtherResult.getData();
                if (order.getStatus() == OrderStatus.NOTPAID.getCode()
                        || order.getStatus() == OrderStatus.PAID.getCode() || order.getStatus() == OrderStatus.CHANGE.getCode()) {
                    Response changeOrderResult = cancelFromOtherOrder(order, headers);
                    if (changeOrderResult.getStatus() == 1) {
                        LOGGER.info("[cancelOrder][Cancel Order Success]");
                        String money = calculateRefund(order);
                        boolean status = drawbackMoney(money, loginId, headers);
                        if (!status) {
                            LOGGER.error("[cancelOrder][Draw Back Money Failed][loginId: {}, orderId: {}]", loginId, orderId);
                        }
                        return new Response<>(1, "Success.", null);
                    } else {
                        LOGGER.error("[cancelOrder][Cancel Order Failed][orderId: {}, Reason: {}]", orderId, changeOrderResult.getMsg());
                        return new Response<>(0, "Fail.Reason:" + changeOrderResult.getMsg(), null);
                    }
                } else {
                    LOGGER.warn("[cancelOrder][Order Status Not Permitted][loginId: {}, orderId: {}]", loginId, orderId);
                    return new Response<>(0, ORDER_STATUS_CANCEL_NOT_PERMITTED, null);
                }
            } else {
                LOGGER.warn("[cancelOrder][Order Not Found][loginId: {}, orderId: {}]", loginId, orderId);
                return new Response<>(0, "Order Not Found.", null);
            }
        }
    }

    @Override
    public Response calculateRefund(String orderId, HttpHeaders headers) {
        Response<Order> orderResult = getOrderByIdFromOrder(orderId, headers);
        if (orderResult.getStatus() == 1) {
            Order order = orderResult.getData();
            if (order.getStatus() == OrderStatus.NOTPAID.getCode() || order.getStatus() == OrderStatus.PAID.getCode()) {
                if (order.getStatus() == OrderStatus.NOTPAID.getCode()) {
                    LOGGER.info("[calculateRefund][Not Paid][orderId: {}]", orderId);
                    return new Response<>(1, "Success. Refund 0", "0");
                } else {
                    LOGGER.info("[calculateRefund][Paid][orderId: {}]", orderId);
                    return new Response<>(1, "Success.", calculateRefund(order));
                }
            } else {
                LOGGER.info("[calculateRefund][Cancel Not Permitted][orderId: {}]", orderId);
                return new Response<>(0, "Order Status Cancel Not Permitted, Refund error", null);
            }
        } else {
            Response<Order> orderOtherResult = getOrderByIdFromOrderOther(orderId, headers);
            if (orderOtherResult.getStatus() == 1) {
                Order order = orderOtherResult.getData();
                if (order.getStatus() == OrderStatus.NOTPAID.getCode() || order.getStatus() == OrderStatus.PAID.getCode()) {
                    if (order.getStatus() == OrderStatus.NOTPAID.getCode()) {
                        LOGGER.info("[calculateRefund][Other Not Paid][orderId: {}]", orderId);
                        return new Response<>(1, "Success, Refund 0", "0");
                    } else {
                        LOGGER.info("[calculateRefund][Other Paid][orderId: {}]", orderId);
                        return new Response<>(1, "Success", calculateRefund(order));
                    }
                } else {
                    LOGGER.warn("[calculateRefund][Other Cancel Not Permitted][orderId: {}]", orderId);
                    return new Response<>(0, ORDER_STATUS_CANCEL_NOT_PERMITTED, null);
                }
            } else {
                LOGGER.error("[calculateRefund][Order not found][orderId: {}]", orderId);
                return new Response<>(0, "Order Not Found", null);
            }
        }
    }

    // -------------------- helper methods --------------------

    private String calculateRefund(Order order) {
        if (order.getStatus() == OrderStatus.NOTPAID.getCode()) {
            return "0.00";
        }
        LOGGER.info("[calculateRefund][Order Travel Date: {}]", order.getTravelDate());
        Date nowDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(StringUtils.String2Date(order.getTravelDate()));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(StringUtils.String2Date(order.getTravelTime()));
        int hour = cal2.get(Calendar.HOUR);
        int minute = cal2.get(Calendar.MINUTE);
        int second = cal2.get(Calendar.SECOND);
        Date startTime = new Date(year, month, day, hour, minute, second); // NOSONAR
        if (nowDate.after(startTime)) {
            LOGGER.warn("[calculateRefund][Ticket expired refund 0]");
            return "0";
        } else {
            double totalPrice = Double.parseDouble(order.getPrice());
            double price = totalPrice * 0.8;
            DecimalFormat priceFormat = new DecimalFormat("0.00");
            return priceFormat.format(price);
        }
    }

    private Response cancelFromOrder(Order order, HttpHeaders headers) {
        LOGGER.info("[cancelFromOrder][Change Order Status]");
        order.setStatus(OrderStatus.CANCEL.getCode());
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<Order> requestEntity = new HttpEntity<>(order, newHeaders);
        String order_service_url = getServiceUrl("ts-order-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                order_service_url + "/api/v1/orderservice/order",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        return re.getBody();
    }

    private Response cancelFromOtherOrder(Order order, HttpHeaders headers) {
        LOGGER.info("[cancelFromOtherOrder][Change Order Status]");
        order.setStatus(OrderStatus.CANCEL.getCode());
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<Order> requestEntity = new HttpEntity<>(order, newHeaders);
        String order_other_service_url = getServiceUrl("ts-order-other-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                order_other_service_url + "/api/v1/orderOtherService/orderOther",
                HttpMethod.PUT,
                requestEntity,
                Response.class);
        return re.getBody();
    }

    private boolean drawbackMoney(String money, String userId, HttpHeaders headers) {
        LOGGER.info("[drawbackMoney][Draw Back Money]");
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(newHeaders);
        String inside_payment_service_url = getServiceUrl("ts-inside-payment-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                inside_payment_service_url + "/api/v1/inside_pay_service/inside_payment/drawback/" + userId + "/" + money,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();
        return result != null && result.getStatus() == 1;
    }

    private Response<User> getAccount(String accountId, HttpHeaders headers) {
        LOGGER.info("[getAccount][Get By Id][accountId: {}]", accountId);
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(newHeaders);
        String user_service_url = getServiceUrl("ts-user-service");
        ResponseEntity<Response<User>> re = restTemplate.exchange(
                user_service_url + "/api/v1/userservice/users/id/" + accountId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<User>>() {});
        return re.getBody();
    }

    private Response<Order> getOrderByIdFromOrder(String orderId, HttpHeaders headers) {
        LOGGER.info("[getOrderByIdFromOrder][orderId: {}]", orderId);
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(newHeaders);
        String order_service_url = getServiceUrl("ts-order-service");
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                order_service_url + "/api/v1/orderservice/order/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {});
        return re.getBody();
    }

    private Response<Order> getOrderByIdFromOrderOther(String orderId, HttpHeaders headers) {
        LOGGER.info("[getOrderByIdFromOrderOther][orderId: {}]", orderId);
        HttpHeaders newHeaders = getAuthorizationHeadersFrom(headers);
        HttpEntity<?> requestEntity = new HttpEntity<>(newHeaders);
        String order_other_service_url = getServiceUrl("ts-order-other-service");
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                order_other_service_url + "/api/v1/orderOtherService/orderOther/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {});
        return re.getBody();
    }

    /**
     * Extract Authorization header if present.
     */
    private static HttpHeaders getAuthorizationHeadersFrom(HttpHeaders oldHeaders) {
        HttpHeaders newHeaders = new HttpHeaders();
        if (oldHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
            newHeaders.add(HttpHeaders.AUTHORIZATION, oldHeaders.getFirst(HttpHeaders.AUTHORIZATION));
        }
        return newHeaders;
    }
}
