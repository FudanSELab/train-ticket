package order.service;

import edu.fudan.common.util.Response;
import order.entity.Assurance;
import org.springframework.http.HttpHeaders;

import javax.transaction.Transactional;
import java.util.UUID;

public interface AssuranceOrderService {
    Response<Assurance> create(int typeIndex, String orderId, HttpHeaders headers);

    @Transactional
    Response<UUID> deleteById(UUID assuranceId, HttpHeaders headers);

    @Transactional
    Response<UUID> deleteByOrderId(UUID orderId, HttpHeaders headers);

    Response<Assurance> changeAssuranceType(String assuranceId, int typeIndex, HttpHeaders headers);
}
