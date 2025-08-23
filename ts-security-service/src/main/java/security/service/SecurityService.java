package security.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface SecurityService {
    Response<String> check(String accountId, HttpHeaders headers);
    Response<Boolean> updateMaxOrderOneHour(Integer value, HttpHeaders headers);
    Response<Boolean> updateMaxOrderNotUse(Integer value, HttpHeaders headers);
}
