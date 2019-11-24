package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.OrderSecurity;

import static org.junit.jupiter.api.Assertions.*;

class OrderSecurityTest {

    @Test
    void getOrderNumInLastOneHour() {
        OrderSecurity OsTest = new OrderSecurity();
        OsTest.setOrderNumInLastOneHour(1);
        Assert.assertEquals(1,OsTest.getOrderNumInLastOneHour());
    }

    @Test
    void setOrderNumInLastOneHour() {
        OrderSecurity OsTest = new OrderSecurity();
        OsTest.setOrderNumInLastOneHour(1);
        Assert.assertEquals(1,OsTest.getOrderNumInLastOneHour());
    }

    @Test
    void getOrderNumOfValidOrder() {
        OrderSecurity OsTest = new OrderSecurity();
        OsTest.setOrderNumOfValidOrder(1);
        Assert.assertEquals(1,OsTest.getOrderNumOfValidOrder());
    }

    @Test
    void setOrderNumOfValidOrder() {
        OrderSecurity OsTest = new OrderSecurity();
        OsTest.setOrderNumOfValidOrder(1);
        Assert.assertEquals(1,OsTest.getOrderNumOfValidOrder());

    }
}