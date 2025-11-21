package order.service;

import edu.fudan.common.util.Response;
import order.entity.WaitListOrder;
import org.springframework.http.HttpHeaders;

public interface WaitListOrderService {

    Response<WaitListOrder> create(WaitListOrder newOrder, HttpHeaders headers);
}
