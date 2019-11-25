package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.OrderStatus;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class OrderStatusTest {
    private OrderStatus notPaidStatus;
    private OrderStatus paidStatus;
    private OrderStatus collectedStatus;
    private OrderStatus changeStatus;
    private OrderStatus cancelStatus;
    private OrderStatus refundsStatus;
    private OrderStatus usedStatus;

    @Before
    public void setUp() {
        notPaidStatus = OrderStatus.NOTPAID;
        paidStatus = OrderStatus.PAID;
        collectedStatus = OrderStatus.COLLECTED;
        changeStatus = OrderStatus.CHANGE;
        cancelStatus = OrderStatus.CANCEL;
        refundsStatus = OrderStatus.REFUNDS;
        usedStatus = OrderStatus.USED;
    }

    @Test
    public void getCode() {
        assertEquals(notPaidStatus.getCode(), 0);
        assertEquals(paidStatus.getCode(), 1);
        assertEquals(collectedStatus.getCode(), 2);
        assertEquals(changeStatus.getCode(), 3);
        assertEquals(cancelStatus.getCode(), 4);
        assertEquals(refundsStatus.getCode(), 5);
        assertEquals(usedStatus.getCode(), 6);
    }

    @Test
    public void getName() {
        assertEquals(notPaidStatus.getName(), "Not Paid");
        assertEquals(paidStatus.getName(), "Paid & Not Collected");
        assertEquals(collectedStatus.getName(), "Collected");
        assertEquals(changeStatus.getName(), "Cancel & Rebook");
        assertEquals(cancelStatus.getName(), "Cancel");
        assertEquals(refundsStatus.getName(), "Refunded");
        assertEquals(usedStatus.getName(), "Used");
    }

    @Test
    public void getNameByCode() {
        assertEquals(OrderStatus.getNameByCode(0), "Not Paid");
        assertEquals(OrderStatus.getNameByCode(1), "Paid & Not Collected");
        assertEquals(OrderStatus.getNameByCode(2), "Collected");
        assertEquals(OrderStatus.getNameByCode(3), "Cancel & Rebook");
        assertEquals(OrderStatus.getNameByCode(4), "Cancel");
        assertEquals(OrderStatus.getNameByCode(5), "Refunded");
        assertEquals(OrderStatus.getNameByCode(6), "Used");
        assertEquals(OrderStatus.getNameByCode(7), "Not Paid");
    }
}