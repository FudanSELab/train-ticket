package preserveOther.service;

import edu.fudan.common.entity.OrderTicketsInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;


/**
 * @author fdse
 */
public interface PreserveOtherService {

    Response preserve(OrderTicketsInfo oti, HttpHeaders headers);
}
