package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignOrder;
import order.repository.ConsignRepository;
import order.service.ConsignPriceService;
import order.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author fdse
 */
@Slf4j
@Service
public class ConsignOrderServiceImpl implements ConsignOrderService {
    @Autowired
    ConsignRepository repository;

    @Autowired
    private ConsignPriceService consignPriceService;

    @Override
    public Response<ConsignOrder> create(ConsignOrder consign, HttpHeaders headers) {
        log.info("[insertConsignRecord][Insert Start][consignRequest.getOrderId: {}]", consign.getOrderId());

        consign.setId(UUID.randomUUID().toString());

        consign.setPrice(fetchPrice(consign.getWeight(), consign.isWithin(), headers));

        log.info("[insertConsignRecord][SAVE consign info][consignRecord : {}]", consign);
        ConsignOrder result = repository.save(consign);
        log.info("[insertConsignRecord][SAVE consign result][result: {}]", result.toString());
        return new Response<>(1, "You have consigned successfully! The price is " + result.getPrice(), result);
    }

    @Override
    public Response<ConsignOrder> update(ConsignOrder consignRequest, HttpHeaders headers) {
        log.info("[updateConsignRecord][Update Start]");

        Optional<ConsignOrder> existingRecord = repository.findById(consignRequest.getId());
        if (!existingRecord.isPresent()) {
            return create(consignRequest, headers);
        }

        ConsignOrder originalRecord = existingRecord.get();
        copyUpdatableFields(originalRecord, consignRequest);
        //Recalculate price
        if (originalRecord.getWeight() != consignRequest.getWeight()) {
            originalRecord.setPrice(fetchPrice(consignRequest.getWeight(), consignRequest.isWithin(), headers));
        }
        originalRecord.setWeight(consignRequest.getWeight());
        ConsignOrder updatedRecord = repository.save(originalRecord);
        return new Response<>(1, "Update consign success", updatedRecord);
    }

    private double fetchPrice(double weight, boolean withinRegion, HttpHeaders headers) {
        return (double) consignPriceService
                .getPriceByWeightAndRegion(weight, withinRegion, headers)
                .getData();
    }

    private void copyUpdatableFields(ConsignOrder target, ConsignOrder source) {
        target.setUserId(source.getUserId());
        target.setHandleDate(source.getHandleDate());
        target.setTargetDate(source.getTargetDate());
        target.setFrom(source.getFrom());
        target.setTo(source.getTo());
        target.setConsignee(source.getConsignee());
        target.setPhone(source.getPhone());
    }
}
