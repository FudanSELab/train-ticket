package preserve.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * Cancel service interface migrated from ts-cancel-service.
 */
public interface CancelService {

    /**
     * Cancel order by order id and login id.
     *
     * @param orderId order id
     * @param loginId login id
     * @param headers headers
     * @return Response result
     */
    Response cancelOrder(String orderId, String loginId, HttpHeaders headers);

    /**
     * Calculate refund for an order.
     *
     * @param orderId order id
     * @param headers headers
     * @return Response containing refund result
     */
    Response calculateRefund(String orderId, HttpHeaders headers);
}
