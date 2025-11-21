package order.service;

import edu.fudan.common.util.Response;
import order.entity.ConsignOrder;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
public interface ConsignOrderService {
    /**
     * query by account id
     *
     * @param userId account id
     * @param headers headers
     * @return Response
     */
    Response<List<ConsignOrder>> getByUserId(UUID userId, HttpHeaders headers);

    /**
     * query by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response<ConsignOrder> getByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * query by consignee
     *
     * @param consignee consignee
     * @param headers headers
     * @return Response
     */
    Response<List<ConsignOrder>> getByConsignee(String consignee, HttpHeaders headers);
}
