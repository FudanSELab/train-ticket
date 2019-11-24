package preserveother.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import preserveother.entity.OrderTicketsInfo;

/**
 * @author fdu
 */
public interface PreserveOtherService {
    /**
     * interface for preserve a ticket
     *
     * @param oti,     order tickets info
     * @param headers, http headers
     * @return
     */
    Response preserve(OrderTicketsInfo oti, HttpHeaders headers);
}
