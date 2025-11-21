package order.service;

import edu.fudan.common.util.Response;
import order.entity.ConsignOrder;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface ConsignOrderService {

    /**
     * insert consign record
     *
     * @param consignRequest consign request
     * @param headers headers
     * @return Response
     */
    Response<ConsignOrder> create(ConsignOrder consignRequest, HttpHeaders headers);

    /**
     * update consign record
     *
     * @param consignRequest consign request
     * @param headers headers
     * @return Response
     */
    Response<ConsignOrder> update(ConsignOrder consignRequest, HttpHeaders headers);
}
