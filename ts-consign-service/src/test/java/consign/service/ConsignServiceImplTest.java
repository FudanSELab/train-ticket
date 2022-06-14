package consign.service;

import consign.entity.Consign;
import consign.entity.ConsignRecord;
import consign.repository.ConsignRepository;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ConsignServiceImplTest {

    @InjectMocks
    private ConsignServiceImpl consignServiceImpl;

    @Mock
    private ConsignRepository repository;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertConsignRecord() {
        HttpEntity requestEntity = new HttpEntity(null, headers);
        Response<Double> response = new Response<>(1, null, 3.0);
        ResponseEntity<Response<Double>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Consign consignRequest = new Consign(UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 1.0, true);
        ConsignRecord consignRecord = new ConsignRecord(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 1.0, 3.0);
        Mockito.when(restTemplate.exchange(
                "http://ts-consign-price-service:16110/api/v1/consignpriceservice/consignprice/" + consignRequest.getWeight() + "/" + consignRequest.isWithin(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Double>>() {
                })).thenReturn(re);
        Mockito.when(repository.save(Mockito.any(ConsignRecord.class))).thenReturn(consignRecord);
        Response result = consignServiceImpl.insertConsignRecord(consignRequest, headers);
        Assert.assertEquals(new Response<>(1, "You have consigned successfully! The price is 3.0", consignRecord), result);
    }

    @Test
    public void testUpdateConsignRecord1() {
        HttpEntity requestEntity = new HttpEntity(null, headers);
        Response<Double> response = new Response<>(1, null, 3.0);
        ResponseEntity<Response<Double>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Consign consignRequest = new Consign(UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 1.0, true);
        ConsignRecord consignRecord = new ConsignRecord(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 2.0, 3.0);
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(consignRecord));
        Mockito.when(restTemplate.exchange(
                "http://ts-consign-price-service:16110/api/v1/consignpriceservice/consignprice/" + consignRequest.getWeight() + "/" + consignRequest.isWithin(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Double>>() {
                })).thenReturn(re);
        Mockito.when(repository.save(Mockito.any(ConsignRecord.class))).thenReturn(null);
        Response result = consignServiceImpl.updateConsignRecord(consignRequest, headers);
        consignRecord.setWeight(1.0);
        Assert.assertEquals(new Response<>(1, "Update consign success", consignRecord), result);
    }

    @Test
    public void testUpdateConsignRecord2() {
        Consign consignRequest = new Consign(UUID.randomUUID().toString(), UUID.randomUUID(), UUID.randomUUID(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 1.0, true);
        ConsignRecord consignRecord = new ConsignRecord(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "handle_date", "target_date", "place_from", "place_to", "consignee", "10001", 1.0, 3.0);
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(consignRecord));
        Mockito.when(repository.save(Mockito.any(ConsignRecord.class))).thenReturn(null);
        Response result = consignServiceImpl.updateConsignRecord(consignRequest, headers);
        Assert.assertEquals(new Response<>(1, "Update consign success", consignRecord), result);
    }

    @Test
    public void testQueryByAccountId1() {
        UUID accountId = UUID.randomUUID();
        ArrayList<ConsignRecord> consignRecords = new ArrayList<>();
        consignRecords.add(new ConsignRecord());
        Mockito.when(repository.findByAccountId(Mockito.anyString())).thenReturn(consignRecords);
        Response result = consignServiceImpl.queryByAccountId(accountId, headers);
        Assert.assertEquals(new Response<>(1, "Find consign by account id success", consignRecords), result);
    }

    @Test
    public void testQueryByAccountId2() {
        UUID accountId = UUID.randomUUID();
        Mockito.when(repository.findByAccountId(Mockito.anyString())).thenReturn(null);
        Response result = consignServiceImpl.queryByAccountId(accountId, headers);
        Assert.assertEquals(new Response<>(0, "No Content according to accountId", null), result);
    }

    @Test
    public void testQueryByOrderId1() {
        UUID orderId = UUID.randomUUID();
        ConsignRecord consignRecords = new ConsignRecord();
        Mockito.when(repository.findByOrderId(Mockito.anyString())).thenReturn(consignRecords);
        Response result = consignServiceImpl.queryByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(1, "Find consign by order id success", consignRecords), result);
    }

    @Test
    public void testQueryByOrderId2() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(repository.findByOrderId(Mockito.anyString())).thenReturn(null);
        Response result = consignServiceImpl.queryByOrderId(orderId, headers);
        Assert.assertEquals(new Response<>(0, "No Content according to order id", null), result);
    }

    @Test
    public void testQueryByConsignee1() {
        ArrayList<ConsignRecord> consignRecords = new ArrayList<>();
        consignRecords.add(new ConsignRecord());
        Mockito.when(repository.findByConsignee(Mockito.anyString())).thenReturn(consignRecords);
        Response result = consignServiceImpl.queryByConsignee("consignee", headers);
        Assert.assertEquals(new Response<>(1, "Find consign by consignee success", consignRecords), result);
    }

    @Test
    public void testQueryByConsignee2() {
        Mockito.when(repository.findByConsignee(Mockito.anyString())).thenReturn(null);
        Response result = consignServiceImpl.queryByConsignee("consignee", headers);
        Assert.assertEquals(new Response<>(0, "No Content according to consignee", null), result);
    }

}
