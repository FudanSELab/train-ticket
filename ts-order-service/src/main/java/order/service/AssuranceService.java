package order.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import javax.transaction.Transactional;
import java.util.UUID;

public interface AssuranceService {
    Response findAssuranceById(UUID id, HttpHeaders headers);

    Response findAssuranceByOrderId(UUID orderId, HttpHeaders headers);

    Response create(int typeIndex, String orderId, HttpHeaders headers);

    @Transactional
    Response deleteById(UUID assuranceId, HttpHeaders headers);

    @Transactional
    Response deleteByOrderId(UUID orderId, HttpHeaders headers);

    Response modify(String assuranceId, String orderId, int typeIndex, HttpHeaders headers);

    Response getAllAssurances(HttpHeaders headers);

    Response getAllAssuranceTypes(HttpHeaders headers);
}
