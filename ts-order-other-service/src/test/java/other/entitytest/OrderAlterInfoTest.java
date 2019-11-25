package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.Order;
import other.entity.OrderAlterInfo;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderAlterInfoTest {

    @Test
    void getAccountId() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        UUID uid = new UUID(3,2);
        qaalert.setAccountId(uid);

        Assert.assertEquals("00000000-0000-0003-0000-000000000002",qaalert.getAccountId().toString());

    }

    @Test
    void setAccountId() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        UUID uid = new UUID(3,2);
        qaalert.setAccountId(uid);
        Assert.assertEquals("00000000-0000-0003-0000-000000000002",qaalert.getAccountId().toString());

    }

    @Test
    void getPreviousOrderId() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        UUID uid = new UUID(3,2);
        qaalert.setPreviousOrderId(uid);
        Assert.assertEquals("00000000-0000-0003-0000-000000000002",qaalert.getPreviousOrderId().toString());

    }

    @Test
    void setPreviousOrderId() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        UUID uid = new UUID(3,2);
        qaalert.setPreviousOrderId(uid);
        Assert.assertEquals("00000000-0000-0003-0000-000000000002",qaalert.getPreviousOrderId().toString());

    }

    @Test
    void getLoginToken() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        qaalert.setLoginToken("test");

        Assert.assertEquals("test",qaalert.getLoginToken());
    }

    @Test
    void setLoginToken() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        qaalert.setLoginToken("test");

        Assert.assertEquals("test",qaalert.getLoginToken());
    }

    @Test
    void getNewOrderInfo() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        Order orders = new Order();
        orders.setStatus(1);
        qaalert.setNewOrderInfo(orders);
        Order neworder =qaalert.getNewOrderInfo();
        Assert.assertEquals(1,neworder.getStatus());

    }

    @Test
    void setNewOrderInfo() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        Order orders = new Order();
        orders.setStatus(1);
        qaalert.setNewOrderInfo(orders);
        Order neworder =qaalert.getNewOrderInfo();
        Assert.assertEquals(1,neworder.getStatus());
//        Assert.assertEquals();
    }

    @Test
    void OrderAlertInfo() {
        OrderAlterInfo qaalert = new OrderAlterInfo();
        Order orders = new Order();
        orders.setStatus(1);
        Assert.assertEquals(1,orders.getStatus());
//        Assert.assertEquals();
    }
}