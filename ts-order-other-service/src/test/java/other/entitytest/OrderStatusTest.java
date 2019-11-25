package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.OrderStatus;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void getCode() {
        OrderStatus test1 ;
    }

    @Test
    void getName() {
    }

    @Test
    void getNameByCode() {

        String test = OrderStatus.getNameByCode(0);
        String test0 = OrderStatus.getNameByCode(1);
        String test1 = OrderStatus.getNameByCode(2);
        String test2 = OrderStatus.getNameByCode(3);
        String test3 = OrderStatus.getNameByCode(4);
        String test4 = OrderStatus.getNameByCode(5);
        String test5 = OrderStatus.getNameByCode(6);

        Assert.assertEquals(test,"Not Paid");
        Assert.assertEquals(test0,"Paid & Not Collected");
        Assert.assertEquals(test1,"Collected");
        Assert.assertEquals(test2,"Cancel & Rebook");
        Assert.assertEquals(test3,"Cancel");
        Assert.assertEquals(test4,"Refunded");
        Assert.assertEquals(test5,"Used");

    }
}