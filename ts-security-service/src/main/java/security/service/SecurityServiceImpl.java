package security.service;

import edu.fudan.common.client.ConfigClient;
import edu.fudan.common.client.OrderClient;
import edu.fudan.common.client.dto.config.ConfigDto;
import edu.fudan.common.client.dto.order.OrderSecurityDto;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private ConfigClient configClient;
    @Autowired
    private OrderClient orderClient;

    public static final String MAX_ORDER_ONE_HOUR = "security_max_order_1_hour";
    public static final String MAX_ORDER_NOT_USE = "security_max_order_not_use";
    public static final int DEFAULT_MAX_ORDER_ONE_HOUR = 20;
    public static final int DEFAULT_MAX_ORDER_NOT_USE = 10;

    @Override
    public Response<String> check(String accountId, HttpHeaders headers) {
        log.debug("[check][Get Order Num Info]: accountId: {}", accountId);
        OrderSecurityDto orderResult = orderClient.getOrderSecurity(new Date(), accountId, headers).getData();
        if (orderResult == null) {
            log.warn("[check][OrderSecurityDto is null][AccountId: {}]", accountId);
            return new Response<>(0, "Failed to get order security info", accountId);
        }
        int oneHourOrderCount = orderResult.getOrderNumInLastOneHour();
        int totalValidOrderCount = orderResult.getOrderNumOfValidOrder();

        if (exceedOneHourLimit(oneHourOrderCount, headers)) {
            log.warn("[check][OneHourLimitExceeded][AccountId: {}] count={} limit exceeded", accountId, oneHourOrderCount);
            return new Response<>(0, "Too many orders in the last one hour", accountId);
        }

        if (exceedNotUsedLimit(totalValidOrderCount, headers)) {
            log.warn("[check][NotUsedLimitExceeded][AccountId: {}] count={} limit exceeded", accountId, totalValidOrderCount);
            return new Response<>(0, "Too many valid orders not used yet", accountId);
        }

        return new Response<>(1, "Success", accountId);
    }



    @Override
    public Response<Boolean> updateMaxOrderOneHour(Integer value, HttpHeaders headers) {
        return updateConfig(MAX_ORDER_ONE_HOUR, "Max order one hour", value.toString(), headers);
    }

    @Override
    public Response<Boolean> updateMaxOrderNotUse(Integer value, HttpHeaders headers) {
        return updateConfig(MAX_ORDER_NOT_USE, "Max order not use", value.toString(), headers);
    }

    /**
     * Check whether orders placed in the last hour exceed the configured upper bound.
     */
    private boolean exceedOneHourLimit(int oneHourOrderCount, HttpHeaders headers) {
        int limit = getConfig(headers, MAX_ORDER_ONE_HOUR, DEFAULT_MAX_ORDER_ONE_HOUR);
        log.info("[exceedOneHourLimit] limit={}, current={}", limit, oneHourOrderCount);
        return oneHourOrderCount > limit;
    }

    /**
     * Check whether total valid (unused) orders exceed the configured upper bound.
     */
    private boolean exceedNotUsedLimit(int totalValidOrderCount, HttpHeaders headers) {
        int limit = getConfig(headers, MAX_ORDER_NOT_USE, DEFAULT_MAX_ORDER_NOT_USE);
        log.info("[exceedNotUsedLimit] limit={}, current={}", limit, totalValidOrderCount);
        return totalValidOrderCount > limit;
    }

    /**
     * Get the configuration value from the config service.
     */
    private int getConfig(HttpHeaders headers, String configName, int defaultValue) {
        try {
            Response<ConfigDto> respHour = configClient.getConfigByName(configName, headers);
            if (respHour != null && respHour.getStatus() == 1) {
                return Integer.parseInt(respHour.getData().getValue());
            }
        } catch (Exception e) {
            log.error("[getConfig] config name: {}, error: {}", configName, e);
        }
        log.warn("[getConfig] config name: {}, use default value: {}", configName, defaultValue);
        return defaultValue;
    }

    private Response<Boolean> updateConfig(String configName, String description, String value, HttpHeaders headers) {
      log.info("[updateConfig] config name: {}, value: {}", configName, value);
      ConfigDto dto = new ConfigDto(configName, value, description);
      try {
          Response<ConfigDto> resp = configClient.updateConfig(dto, headers);
          boolean success = resp != null && resp.getStatus() == 1;
          log.info("[updateConfig] config name: {}, value: {} success: {}", configName, value, success);
          return new Response<>(success ? 1 : 0, resp != null ? resp.getMsg() : "Fail", success);
      } catch (Exception e) {
          log.error("[updateConfigInternal] error", e);
          return new Response<>(0, e.getMessage(), false);
      }
  }
}
