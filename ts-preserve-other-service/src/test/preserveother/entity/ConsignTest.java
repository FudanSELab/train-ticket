package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Consign;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class ConsignTest {
    private UUID id;
    private UUID orderId;
    private UUID accountId;
    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean isWithin;
    private Consign consign;

    @Before
    public void setUp() {
        id = UUID.randomUUID();
        orderId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        handleDate = "2019-11-24";
        targetDate = "2019-11-27";
        from = "shanghai";
        to = "beijing";
        consignee = "xiaoming";
        phone = "10086";
        weight = 1.1;
        isWithin = true;
        consign = new Consign();
    }

    @Test
    public void getId() {
        assertNull(consign.getId());
    }

    @Test
    public void getOrderId() {
        assertNull(consign.getOrderId());
    }

    @Test
    public void getAccountId() {
        assertNull(consign.getAccountId());
    }

    @Test
    public void getHandleDate() {
        assertNull(consign.getHandleDate());
    }

    @Test
    public void getTargetDate() {
        assertNull(consign.getTargetDate());
    }

    @Test
    public void getFrom() {
        assertNull(consign.getFrom());
    }

    @Test
    public void getTo() {
        assertNull(consign.getTo());
    }

    @Test
    public void getConsignee() {
        assertNull(consign.getConsignee());
    }

    @Test
    public void getPhone() {
        assertNull(consign.getPhone());
    }

    @Test
    public void getWeight() {
        assertEquals(consign.getWeight(), 0.0, 0.1);
    }

    @Test
    public void isWithin() {
        assertFalse(consign.isWithin());
    }

    @Test
    public void setId() {
        consign.setId(id);
        assertEquals(consign.getId(), id);
    }

    @Test
    public void setOrderId() {
        consign.setOrderId(orderId);
        assertEquals(consign.getOrderId(), orderId);
    }

    @Test
    public void setAccountId() {
        consign.setAccountId(accountId);
        assertEquals(consign.getAccountId(), accountId);
    }

    @Test
    public void setHandleDate() {
        consign.setHandleDate(handleDate);
        assertEquals(consign.getHandleDate(), handleDate);
    }

    @Test
    public void setTargetDate() {
        consign.setTargetDate(targetDate);
        assertEquals(consign.getTargetDate(), targetDate);
    }

    @Test
    public void setFrom() {
        consign.setFrom(from);
        assertEquals(consign.getFrom(), from);
    }

    @Test
    public void setTo() {
        consign.setTo(to);
        assertEquals(consign.getTo(), to);
    }

    @Test
    public void setConsignee() {
        consign.setConsignee(consignee);
        assertEquals(consign.getConsignee(), consignee);
    }

    @Test
    public void setPhone() {
        consign.setPhone(phone);
        assertEquals(consign.getPhone(), phone);
    }

    @Test
    public void setWeight() {
        consign.setWeight(weight);
        assertEquals(consign.getWeight(), weight, 0.1);
    }

    @Test
    public void setWithin() {
        consign.setWithin(isWithin);
        assertEquals(consign.isWithin(), isWithin);
    }

    @Test
    public void testOther() {
        Consign consign1 = new Consign(id, orderId, accountId, handleDate, targetDate, from, to, consignee, phone, weight, isWithin);
        assertNotEquals(consign, consign1);
        assertNotNull(consign1.hashCode());
        assertNotNull(consign1.toString());
    }
}