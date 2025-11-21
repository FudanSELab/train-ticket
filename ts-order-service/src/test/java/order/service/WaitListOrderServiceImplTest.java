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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.verify;
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
    public void create_shouldFailWhenUserIdMissing() {
        WaitListOrder order = new WaitListOrder();
        order.setUserId(null);

        Response<WaitListOrder> response = waitListOrderService.create(order, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("UserId is required", response.getMsg());
    }

    @Test
    public void create_shouldFailWhenDuplicateOrderExists() {
        String userId = "user-1";
        WaitListOrder existing = buildWaitListOrder(userId, WaitListOrderStatus.NOTPAID.getCode());
        WaitListOrder duplicate = buildWaitListOrder(userId, WaitListOrderStatus.NOTPAID.getCode());
        duplicate.setTravelTime(existing.getTravelTime());

        ArrayList<WaitListOrder> userOrders = new ArrayList<>();
        userOrders.add(existing);

        when(waitListOrderRepository.findByUserId(userId)).thenReturn(userOrders);

        Response<WaitListOrder> response = waitListOrderService.create(duplicate, headers);

        Assertions.assertEquals(0, response.getStatus());
        Assertions.assertEquals("Order already exist", response.getMsg());
    }

    @Test
    public void create_shouldPersistWhenOrderIsUnique() {
        String userId = "user-1";
        WaitListOrder newOrder = buildWaitListOrder(userId, WaitListOrderStatus.NOTPAID.getCode());

        when(waitListOrderRepository.findByUserId(userId)).thenReturn(new ArrayList<>());
        when(waitListOrderRepository.save(Mockito.any(WaitListOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Response<WaitListOrder> response = waitListOrderService.create(newOrder, headers);

        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals("Success.", response.getMsg());
        Assertions.assertNotNull(response.getData().getId());
        verify(waitListOrderRepository).save(Mockito.any(WaitListOrder.class));
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


