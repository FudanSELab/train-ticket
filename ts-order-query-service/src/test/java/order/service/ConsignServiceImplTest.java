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
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ConsignServiceImplTest {

    @InjectMocks
    private ConsignOrderServiceImpl consignServiceImpl;

    @Mock
    private ConsignRepository repository;

    private final HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getByUserId_shouldReturnRecordsWhenPresent() {
        UUID accountId = UUID.randomUUID();
        ArrayList<ConsignOrder> consignRecords = new ArrayList<>();
        consignRecords.add(new ConsignOrder());
        when(repository.findByAccountId(accountId.toString())).thenReturn(consignRecords);

        Response<List<ConsignOrder>> result = consignServiceImpl.getByUserId(accountId, headers);

        Assertions.assertEquals(new Response<>(1, "Find consign by account id success", consignRecords), result);
    }

    @Test
    public void getByUserId_shouldReturnNoContentWhenEmpty() {
        UUID accountId = UUID.randomUUID();
        when(repository.findByAccountId(accountId.toString())).thenReturn(null);

        Response<List<ConsignOrder>> result = consignServiceImpl.getByUserId(accountId, headers);

        Assertions.assertEquals(new Response<>(0, "No Content according to accountId", null), result);
    }

    @Test
    public void getByOrderId_shouldReturnRecordWhenPresent() {
        UUID orderId = UUID.randomUUID();
        ConsignOrder consignRecord = new ConsignOrder();
        when(repository.findByOrderId(orderId.toString())).thenReturn(consignRecord);

        Response<ConsignOrder> result = consignServiceImpl.getByOrderId(orderId, headers);

        Assertions.assertEquals(new Response<>(1, "Find consign by order id success", consignRecord), result);
    }

    @Test
    public void getByOrderId_shouldReturnNoContentWhenMissing() {
        UUID orderId = UUID.randomUUID();
        when(repository.findByOrderId(orderId.toString())).thenReturn(null);

        Response<ConsignOrder> result = consignServiceImpl.getByOrderId(orderId, headers);

        Assertions.assertEquals(new Response<>(0, "No Content according to order id", null), result);
    }

    @Test
    public void getByConsignee_shouldReturnRecords() {
        ArrayList<ConsignOrder> consignRecords = new ArrayList<>();
        consignRecords.add(new ConsignOrder());
        when(repository.findByConsignee("consignee")).thenReturn(consignRecords);

        Response<List<ConsignOrder>> result = consignServiceImpl.getByConsignee("consignee", headers);

        Assertions.assertEquals(new Response<>(1, "Find consign by consignee success", consignRecords), result);
    }

    @Test
    public void getByConsignee_shouldReturnNoContentWhenEmpty() {
        when(repository.findByConsignee("consignee")).thenReturn(null);

        Response<List<ConsignOrder>> result = consignServiceImpl.getByConsignee("consignee", headers);

        Assertions.assertEquals(new Response<>(0, "No Content according to consignee", null), result);
    }
}
