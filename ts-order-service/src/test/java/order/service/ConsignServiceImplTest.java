package order.service;

import edu.fudan.common.util.Response;
import order.entity.ConsignOrder;
import order.repository.ConsignRepository;
import order.service.impl.ConsignOrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

@RunWith(JUnit4.class)
public class ConsignServiceImplTest {

    @InjectMocks
    private ConsignOrderServiceImpl consignServiceImpl;

    @Mock
    private ConsignRepository repository;

    @Mock
    private ConsignPriceService consignPriceService;

    private final HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertConsignRecord() {
        ConsignOrder consignRequest = buildConsignRequest(1.0);
        ConsignOrder consignRecord = buildConsignEntity(1.0, 3.0);
        Mockito.when(consignPriceService.getPriceByWeightAndRegion(Mockito.eq(consignRequest.getWeight()),
                        Mockito.eq(consignRequest.isWithin()), Mockito.any(HttpHeaders.class)))
                .thenReturn(new Response<>(1, null, 3.0));
        Mockito.when(repository.save(Mockito.any(ConsignOrder.class))).thenReturn(consignRecord);
        Response<ConsignOrder> result = consignServiceImpl.create(consignRequest, headers);
        Assertions.assertEquals(new Response<>(1, "You have consigned successfully! The price is 3.0", consignRecord), result);
    }

    @Test
    public void testUpdateConsignRecordRecalculatePrice() {
        ConsignOrder consignRequest = buildConsignRequest(1.0);
        ConsignOrder consignRecord = buildConsignEntity(2.0, 1.0);
        consignRequest.setId(consignRecord.getId());
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(consignRecord));
        Mockito.when(consignPriceService.getPriceByWeightAndRegion(Mockito.eq(consignRequest.getWeight()),
                        Mockito.eq(consignRequest.isWithin()), Mockito.any(HttpHeaders.class)))
                .thenReturn(new Response<>(1, null, 3.0));
        Mockito.when(repository.save(Mockito.any(ConsignOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<ConsignOrder> result = consignServiceImpl.update(consignRequest, headers);
        consignRecord.setWeight(consignRequest.getWeight());
        consignRecord.setPrice(3.0);
        Assertions.assertEquals(new Response<>(1, "Update consign success", consignRecord), result);
    }

    @Test
    public void testUpdateConsignRecordNoRecalculate() {
        ConsignOrder consignRequest = buildConsignRequest(1.0);
        ConsignOrder consignRecord = buildConsignEntity(1.0, 3.0);
        consignRequest.setId(consignRecord.getId());
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(consignRecord));
        Mockito.when(repository.save(Mockito.any(ConsignOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<ConsignOrder> result = consignServiceImpl.update(consignRequest, headers);
        Assertions.assertEquals(new Response<>(1, "Update consign success", consignRecord), result);
    }

    private ConsignOrder buildConsignRequest(double weight) {
        ConsignOrder consign = new ConsignOrder();
        consign.setOrderId(UUID.randomUUID().toString());
        consign.setUserId(UUID.randomUUID().toString());
        consign.setHandleDate("handle_date");
        consign.setTargetDate("target_date");
        consign.setFrom("place_from");
        consign.setTo("place_to");
        consign.setConsignee("consignee");
        consign.setPhone("10001");
        consign.setWeight(weight);
        consign.setWithin(true);
        return consign;
    }

    private ConsignOrder buildConsignEntity(double weight, double price) {
        ConsignOrder consign = buildConsignRequest(weight);
        consign.setId(UUID.randomUUID().toString());
        consign.setPrice(price);
        return consign;
    }
}
