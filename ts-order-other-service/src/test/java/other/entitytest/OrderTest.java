package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.method.P;
import other.entity.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void getId() {
        Order order = new Order();
        UUID uid = new UUID(123,321);
        order.setId(uid);
        order.getId();
        Assert.assertEquals("00000000-0000-007b-0000-000000000141",uid.toString());
    }

    @Test
    void setId() {
        Order order = new Order();
        UUID uid = new UUID(123,321);
        order.setId(uid);
        order.getId();
        Assert.assertEquals("00000000-0000-007b-0000-000000000141",uid.toString());
    }

    @Test
    void getAccountId() {
        Order order = new Order();
        UUID uid = new UUID(11,12);
        order.setAccountId(uid);
        order.getAccountId();
        Assert.assertEquals("00000000-0000-000b-0000-00000000000c",order.getAccountId().toString());


    }

    @Test
    void setAccountId() {
        Order order = new Order();
        UUID uid = new UUID(11,12);
        order.setAccountId(uid);
        order.getAccountId();
        Assert.assertEquals("00000000-0000-000b-0000-00000000000c",order.getAccountId().toString());
    }

    @Test
    void getBoughtDate() {
        Order order = new Order();
        Assert.assertEquals(order.getBoughtDate().toString(), new Date(System.currentTimeMillis()).toString());

    }

    @Test
    void setBoughtDate() {
        Order order = new Order();
        order.setBoughtDate(new Date(123456789));
        Assert.assertEquals("Fri Jan 02 18:17:36 CST 1970",order.getTravelDate().toString());
    }

    @Test
    void getTravelDate() {
        Order order = new Order();
        Assert.assertEquals("Fri Jan 02 18:17:36 CST 1970",order.getTravelDate().toString());
    }

    @Test
    void setTravelDate() {
        Order order = new Order();
        order.setTravelDate(new Date(123456789));
        Assert.assertEquals("Fri Jan 02 18:17:36 CST 1970",order.getTravelDate().toString());

    }

    @Test
    void getTravelTime() {
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        Order order = new Order();
        order.setTravelTime(starttime);
        Assert.assertEquals(order.getTravelTime().toString(),"Tue Dec 02 01:01:01 CST 2014");


    }

    @Test
    void setTravelTime() {
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        Order order = new Order();
        order.setTravelTime(starttime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",order.getTravelTime().toString());
    }


    @Test
    void getTrainNumber() {
        Order order = new Order();
        Assert.assertEquals(order.getTrainNumber(),"G1235");
    }

    @Test
    void setTrainNumber() {
        Order order = new Order();
        order.setTrainNumber("G1234");
        Assert.assertEquals(order.getTrainNumber(),"G1234");
    }

    @Test
    void getCoachNumber() {
        Order order = new Order();
        Assert.assertEquals(5,order.getCoachNumber());
    }

    @Test
    void setCoachNumber() {
        Order order = new Order();
        order.setCoachNumber(4);
        Assert.assertEquals(4,order.getCoachNumber());
    }

    @Test
    void getSeatClass() {
        Order order = new Order();
        Assert.assertEquals(order.getStatus(),1);
    }

    @Test
    void setSeatClass() {
        Order order = new Order();
        order.setSeatClass(3);
        Assert.assertEquals(order.getSeatClass(),3);
    }

    @Test
    void getSeatNumber() {
        Order order = new Order();
        Assert.assertEquals(order.getSeatNumber(),"5A");
    }

    @Test
    void setSeatNumber() {
        Order order = new Order();
        order.setSeatNumber("6A");
        Assert.assertEquals(order.getSeatNumber(),"6A");
    }

    @Test
    void getFrom() {
        Order order = new Order();
        Assert.assertEquals(order.getFrom(),"shanghai");
    }

    @Test
    void setFrom() {
        Order order = new Order();
        order.setFrom("shenzhen");
        Assert.assertEquals(order.getFrom(),"shenzhen");
    }

    @Test
    void getTo() {
        Order order = new Order();
        Assert.assertEquals(order.getTo(),"taiyuan");

    }

    @Test
    void setTo() {
        Order order = new Order();
        order.setTo("shenzhen");
        Assert.assertEquals(order.getTo(),"shenzhen");
    }

    @Test
    void getStatus() {
        Order order = new Order();
        Assert.assertEquals(order.getStatus(),1);

    }

    @Test
    void setStatus() {
        Order order = new Order();
        order.setStatus(2);
        Assert.assertEquals(order.getStatus(),2);
    }

    @Test
    void getPrice() {
        Order order =  new Order();
        Assert.assertEquals(order.getPrice(),"0.0");
    }

    @Test
    void setPrice() {
        Order order =  new Order();
        order.setPrice("10.0");
        Assert.assertEquals(order.getPrice(),"10.0");
    }

    @Test
    void getContactsName() {
        Order order = new Order();
        order.setContactsName("test");
        Assert.assertEquals(order.getContactsName(), "test");
    }

    @Test
    void setContactsName() {
        Order order = new Order();
        order.setContactsName("test");
        Assert.assertEquals(order.getContactsName(), "test");
    }

    @Test
    void getDocumentType() {
        Order order = new Order();
        order.setDocumentType(1);
        Assert.assertEquals(order.getDocumentType(),1);
    }

    @Test
    void setDocumentType() {
        Order order = new Order();
        order.setDocumentType(1);
        Assert.assertEquals(order.getDocumentType(),1);
    }

    @Test
    void getContactsDocumentNumber() {
        Order order = new Order();
        order.setContactsDocumentNumber("test");
        Assert.assertEquals(order.getContactsDocumentNumber(), "test");

    }

    @Test
    void setContactsDocumentNumber() {
        Order order = new Order();
        order.setContactsDocumentNumber("test");
        Assert.assertEquals(order.getContactsDocumentNumber(), "test");
    }
}