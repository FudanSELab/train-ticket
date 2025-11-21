package order.service;

import edu.fudan.common.util.Response;
import order.entity.WaitListOrder;
import order.entity.WaitListOrderStatus;
import order.repository.WaitListOrderRepository;
import order.service.impl.WaitListOrderServiceImpl;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class WaitListOrderServiceImplTest {

    @InjectMocks
    private WaitListOrderServiceImpl waitListOrderService;

    @Mock
    private WaitListOrderRepository waitListOrderRepository;

    private HttpHeaders headers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        headers = new HttpHeaders();
    }

    @Test
    public void getAllWaitListOrders_shouldReturnNoContentWhenEmpty() {
        when(waitListOrderRepository.findAll()).thenReturn(Collections.emptyList());

        Response<List<WaitListOrder>> response = waitListOrderService.getAllWaitListOrders(headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("No Content.", response.getMsg());
    }

    @Test
    public void getAllWaitListOrders_shouldReturnOrders() {
        List<WaitListOrder> orders = Collections.singletonList(
                buildWaitListOrder("user-1", WaitListOrderStatus.NOTPAID.getCode()));
        when(waitListOrderRepository.findAll()).thenReturn(new ArrayList<>(orders));

        Response<List<WaitListOrder>> response = waitListOrderService.getAllWaitListOrders(headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertEquals(1, response.getData().size());
    }

    @Test
    public void getAllActiveWaitListOrders_shouldFilterInactiveOrders() {
        WaitListOrder active1 = buildWaitListOrder("user-1", WaitListOrderStatus.NOTPAID.getCode());
        WaitListOrder active2 = buildWaitListOrder("user-2", WaitListOrderStatus.PAID.getCode());
        WaitListOrder inactive = buildWaitListOrder("user-3", WaitListOrderStatus.CANCEL.getCode());

        when(waitListOrderRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(active1, active2, inactive)));

        Response<List<WaitListOrder>> response = waitListOrderService.getAllActiveWaitListOrders(headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertEquals(2, response.getData().size());
        Assertions.assertTrue(response.getData().stream().allMatch(order ->
                order.getStatus() == WaitListOrderStatus.NOTPAID.getCode()
                        || order.getStatus() == WaitListOrderStatus.PAID.getCode()));
    }

    private WaitListOrder buildWaitListOrder(String userId, int status) {
        WaitListOrder order = new WaitListOrder();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setContactsId("contacts-" + userId);
        order.setTrainNumber("G1234");
        order.setFromStation("shanghai");
        order.setToStation("beijing");
        order.setStatus(status);
        return order;
    }
}

