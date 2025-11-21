package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignOrder;
import order.repository.ConsignRepository;
import order.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {
    @Autowired
    ConsignRepository repository;

    @Override
    public Response<List<ConsignOrder>> getByUserId(UUID accountId, HttpHeaders headers) {
        List<ConsignOrder> consignRecords = repository.findByAccountId(accountId.toString());
        if (consignRecords == null || consignRecords.isEmpty()) {
            log.warn("[queryByAccountId][No Content according to accountId][accountId: {}]", accountId);
            return new Response<>(0, "No Content according to accountId", null);
        }
        return new Response<>(1, "Find consign by account id success", consignRecords);
    }

    @Override
    public Response<ConsignOrder> getByOrderId(UUID orderId, HttpHeaders headers) {
        ConsignOrder consignRecord = repository.findByOrderId(orderId.toString());
        if (consignRecord == null) {
            log.warn("[queryByOrderId][No Content according to orderId][orderId: {}]", orderId);
            return new Response<>(0, "No Content according to order id", null);
        }
        return new Response<>(1, "Find consign by order id success", consignRecord);
    }

    @Override
    public Response<List<ConsignOrder>> getByConsignee(String consignee, HttpHeaders headers) {
        List<ConsignOrder> consignRecords = repository.findByConsignee(consignee);
        if (consignRecords == null || consignRecords.isEmpty()) {
            log.warn("[queryByConsignee][No Content according to consignee][consignee: {}]", consignee);
            return new Response<>(0, "No Content according to consignee", null);
        }
        return new Response<>(1, "Find consign by consignee success", consignRecords);
    }
}
