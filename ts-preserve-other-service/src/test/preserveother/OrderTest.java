package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class OrderTest {
    private UUID testId;
    private Date testBoughtDate;
    private Date testTravelDate;
    private Date testTravelTime;
    private UUID testAccountId;
    private String testContactsName;
    private int testDocumentType;
    private String testContactsDocumentNumber;
    private String testTrainNumber;
    private int testCoachNumber;
    private int testSeatClass;
    private String testSeatNumber;
    private String testFrom;
    private String testTo;
    private int testStatus;
    private String testPrice;

    private Order testOrder;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        testBoughtDate = new Date(System.currentTimeMillis());
        testTravelDate = new Date(123456789);
        testTravelTime = new Date(111111);
        testAccountId = UUID.randomUUID();
        testContactsName = "xiaoming";
        testDocumentType = 1;
        testContactsDocumentNumber = "100";
        testTrainNumber = "K1048";
        testCoachNumber = 9527;
        testSeatClass = 69;
        testSeatNumber = "5A";
        testFrom = "shanghai";
        testTo = "beijing";
        testStatus = 1;
        testPrice = "200";

        testOrder = new Order(testId, testBoughtDate, testTravelDate, testTravelTime, testAccountId, testContactsName,
                testDocumentType, testContactsDocumentNumber, testTrainNumber, testCoachNumber, testSeatClass, testSeatNumber, testFrom,
                testTo, testStatus, testPrice);

    }

    @Test
    public void testEquals() {
        Order order = new Order(testId, testBoughtDate, testTravelDate, testTravelTime, testAccountId, testContactsName,
                testDocumentType, testContactsDocumentNumber, testTrainNumber, testCoachNumber, testSeatClass, testSeatNumber, testFrom,
                testTo, testStatus, testPrice);
        assertTrue(testOrder.equals(order));
        assertFalse(testOrder.equals(null));
        assertTrue(testOrder.equals(testOrder));
        assertFalse(testOrder.equals(testAccountId));
    }

    @Test
    public void testHashCode() {
        assertNotEquals(testOrder.hashCode(), 0);

    }

    @Test
    public void getId() {
        assertEquals(testOrder.getId(), testId);
    }

    @Test
    public void setId() {
        UUID newId = UUID.randomUUID();
        testOrder.setId(newId);
        assertEquals(testOrder.getId(), newId);
    }

    @Test
    public void getAccountId() {
        assertEquals(testOrder.getAccountId(), testAccountId);
    }

    @Test
    public void setAccountId() {
        UUID newAccountId = UUID.randomUUID();
        testOrder.setAccountId(newAccountId);
        assertEquals(testOrder.getAccountId(), newAccountId);
    }

    @Test
    public void getBoughtDate() {
        assertEquals(testOrder.getBoughtDate(), testBoughtDate);
    }

    @Test
    public void setBoughtDate() {
        Date newBoughtDate = new Date(111111);
        testOrder.setBoughtDate(newBoughtDate);
        assertEquals(testOrder.getBoughtDate(), newBoughtDate);
    }

    @Test
    public void getTravelDate() {
        assertEquals(testOrder.getTravelDate(), testTravelDate);
    }

    @Test
    public void setTravelDate() {
        Date newTravelDate = new Date(222222);
        testOrder.setTravelDate(newTravelDate);
        assertEquals(testOrder.getTravelDate(), newTravelDate);
    }

    @Test
    public void testSetTravelDate() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019, 9, 22, 0, 0, 0);
        Date newTravelDate = cld.getTime();
        testOrder.setTravelDate(2019, 10, 22);
        assertEquals(testOrder.getTravelDate(), newTravelDate);
    }

    @Test
    public void getTravelTime() {
        assertEquals(testOrder.getTravelTime(), testTravelTime);
    }

    @Test
    public void setTravelTime() {
        Date newTravelTime = new Date(333333);
        testOrder.setTravelTime(newTravelTime);
        assertEquals(testOrder.getTravelTime(), newTravelTime);
    }

    @Test
    public void testSetTravelTime() {
        Calendar cld = Calendar.getInstance();
        cld.set(1970, 0, 1, 19, 10, 0);
        Date newTravelTime = cld.getTime();
        testOrder.setTravelTime(19, 10);
        assertEquals(testOrder.getTravelTime(), newTravelTime);
    }

    @Test
    public void getTrainNumber() {
        assertEquals(testOrder.getTrainNumber(), testTrainNumber);
    }

    @Test
    public void setTrainNumber() {
        String newTrainNumber = "K1047";
        testOrder.setTrainNumber(newTrainNumber);
        assertEquals(testOrder.getTrainNumber(), newTrainNumber);
    }

    @Test
    public void getCoachNumber() {
        assertEquals(testOrder.getCoachNumber(), testCoachNumber);
    }

    @Test
    public void setCoachNumber() {
        int newCoachNumber = 2;
        testOrder.setCoachNumber(newCoachNumber);
        assertEquals(testOrder.getCoachNumber(), newCoachNumber);
    }

    @Test
    public void getSeatClass() {
        assertEquals(testOrder.getSeatClass(), testSeatClass);
    }

    @Test
    public void setSeatClass() {
        int newSeatClass = 77;
        testOrder.setSeatClass(newSeatClass);
        assertEquals(testOrder.getSeatClass(), newSeatClass);
    }

    @Test
    public void getSeatNumber() {
        assertEquals(testOrder.getSeatNumber(), testSeatNumber);
    }

    @Test
    public void setSeatNumber() {
        String newSeatNumber = "6B";
        testOrder.setSeatNumber(newSeatNumber);
        assertEquals(testOrder.getSeatNumber(), newSeatNumber);
    }

    @Test
    public void getFrom() {
        assertEquals(testOrder.getFrom(), testFrom);
    }

    @Test
    public void setFrom() {
        String newFrom = "hunan";
        testOrder.setFrom(newFrom);
        assertEquals(testOrder.getFrom(), newFrom);
    }

    @Test
    public void getTo() {
        assertEquals(testOrder.getTo(), testTo);
    }

    @Test
    public void setTo() {
        String newTo = "tianjin";
        testOrder.setTo(newTo);
        assertEquals(testOrder.getTo(), newTo);
    }

    @Test
    public void getStatus() {
        assertEquals(testOrder.getStatus(), testStatus);
    }

    @Test
    public void setStatus() {
        int newStatus = 2;
        testOrder.setStatus(newStatus);
        assertEquals(testOrder.getStatus(), newStatus);
    }

    @Test
    public void getPrice() {
        assertEquals(testOrder.getPrice(), testPrice);
    }

    @Test
    public void setPrice() {
        String newPrice = "1000";
        testOrder.setPrice(newPrice);
        assertEquals(testOrder.getPrice(), newPrice);
    }

    @Test
    public void getContactsName() {
        assertEquals(testOrder.getContactsName(), testContactsName);
    }

    @Test
    public void setContactsName() {
        String newContactsName = "lihua";
        testOrder.setContactsName(newContactsName);
        assertEquals(testOrder.getContactsName(), newContactsName);
    }

    @Test
    public void getDocumentType() {
        assertEquals(testOrder.getDocumentType(), testDocumentType);
    }

    @Test
    public void setDocumentType() {
        int newDocumentType = 2;
        testOrder.setDocumentType(newDocumentType);
        assertEquals(testOrder.getDocumentType(), newDocumentType);
    }

    @Test
    public void getContactsDocumentNumber() {
        assertEquals(testOrder.getContactsDocumentNumber(), testContactsDocumentNumber);
    }

    @Test
    public void setContactsDocumentNumber() {
        String newContactsDocumentNumber = "333";
        testOrder.setContactsDocumentNumber(newContactsDocumentNumber);
        assertEquals(testOrder.getContactsDocumentNumber(), newContactsDocumentNumber);
    }

    @Test
    public void testOther() {
        Order order1 = new Order();
        assertEquals(order1.getTrainNumber(), "G1235");
    }
}