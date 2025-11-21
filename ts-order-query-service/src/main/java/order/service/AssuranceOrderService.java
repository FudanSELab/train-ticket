package order.service;

import edu.fudan.common.client.dto.order.AssuranceOrderDto;
import edu.fudan.common.client.dto.order.AssuranceTypeDto;
import edu.fudan.common.util.Response;
import order.entity.AssuranceOrder;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssuranceOrderService {
    Response<Optional<AssuranceOrder>> findAssuranceById(UUID id, HttpHeaders headers);

    Response<AssuranceOrder> findAssuranceByOrderId(UUID orderId, HttpHeaders headers);

    Response<List<AssuranceOrderDto>> getAllAssurances(HttpHeaders headers);

    Response<List<AssuranceTypeDto>> getAllAssuranceTypes(HttpHeaders headers);
}
