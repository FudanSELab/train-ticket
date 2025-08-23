package edu.fudan.common.client;

import edu.fudan.common.client.dto.order.OrderSecurityDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client for interacting with the Order Service.
 */
@Slf4j
@Component
public class OrderClient {

    private static final String SERVICE_NAME = "ts-order-service";
    private static final String BASE_URL = "/api/v1/order";

    @Autowired
    private RestTemplate restTemplate;

    private String getServiceUrl() {
        return "http://" + SERVICE_NAME;
    }

    // FIXME 该接口暂未实现，先声明占用给 ts-security-service 使用
    /**
     * Retrieve order security statistics for specified account at given time.
     * Endpoint: /order/order/security/{date}/{accountId}
     */
    public Response<OrderSecurityDto> getOrderSecurity(Date date, String accountId, HttpHeaders headers) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateStr = sdf.format(date);
        log.info("[getOrderSecurity][date: {}][accountId: {}]", dateStr, accountId);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Response<OrderSecurityDto>> response = restTemplate.exchange(
                getServiceUrl() + BASE_URL + "/order/order/security/" + dateStr + "/" + accountId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Response<OrderSecurityDto>>() {
                });
        return response.getBody();
    }
}
