package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.OrderTicketsInfo;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class OrderTicketsInfoTest {
    private String accountId;
    private String contactsId;
    private String tripId;
    private int seatType;
    private Date date;
    private String from;
    private String to;
    private int assurance;
    private int foodType;
    private String stationName;
    private String storeName;
    private String foodName;
    private double foodPrice;
    private String handleDate;
    private String consigneeName;
    private String consigneePhone;
    private double consigneeWeight;
    private boolean isWithin;
    private OrderTicketsInfo orderTicketsInfo;

    @Before
    public void setUp() {
        accountId = "111";
        contactsId = "222";
        tripId = "333";
        seatType = 1;
        date = new Date(444444);
        from = "shanghai";
        to = "to";
        assurance = 1;
        foodType = 1;
        stationName = "shanghaistation";
        storeName = "familymart";
        foodName = "bread";
        foodPrice = 6.6;
        handleDate = "2019-11-23";
        consigneeName = "xiaoming";
        consigneePhone = "10086";
        consigneeWeight = 100.0;
        isWithin = true;
        orderTicketsInfo = new OrderTicketsInfo(accountId, contactsId, tripId, seatType, date, from, to, assurance,
                foodType, stationName, storeName, foodName, foodPrice, handleDate, consigneeName, consigneePhone, consigneeWeight, isWithin);
    }

    @Test
    public void testOrderTicksInfo() {
        assertEquals(new OrderTicketsInfo().getFoodType(), 0);
    }

    @Test
    public void getAccountId() {
        assertEquals(orderTicketsInfo.getAccountId(), accountId);
    }

    @Test
    public void setAccountId() {
        String newAccountId = "222";
        orderTicketsInfo.setAccountId(newAccountId);
        assertEquals(orderTicketsInfo.getAccountId(), newAccountId);
    }

    @Test
    public void getFoodType() {
        assertEquals(orderTicketsInfo.getFoodType(), foodType);
    }

    @Test
    public void setFoodType() {
        int newFoodType = 2;
        orderTicketsInfo.setFoodType(newFoodType);
        assertEquals(orderTicketsInfo.getFoodType(), newFoodType);

    }

    @Test
    public void getStationName() {
        assertEquals(orderTicketsInfo.getStationName(), stationName);
    }

    @Test
    public void setStationName() {
        String newStationName = "hunan";
        orderTicketsInfo.setStationName(newStationName);
        assertEquals(orderTicketsInfo.getStationName(), newStationName);
    }

    @Test
    public void getStoreName() {
        assertEquals(orderTicketsInfo.getStoreName(), storeName);
    }

    @Test
    public void setStoreName() {
        String newStoreName = "lawson";
        orderTicketsInfo.setStoreName(newStoreName);
        assertEquals(orderTicketsInfo.getStoreName(), newStoreName);

    }

    @Test
    public void getFoodName() {
        assertEquals(orderTicketsInfo.getFoodName(), foodName);
    }

    @Test
    public void setFoodName() {
        String newFoodName = "pizza";
        orderTicketsInfo.setFoodName(newFoodName);
        assertEquals(orderTicketsInfo.getFoodName(), newFoodName);

    }

    @Test
    public void getFoodPrice() {
        assertEquals(orderTicketsInfo.getFoodPrice(), foodPrice, 0.1);
    }

    @Test
    public void setFoodPrice() {
        Double newFoodPrice = 9.9;
        orderTicketsInfo.setFoodPrice(newFoodPrice);
        assertEquals(orderTicketsInfo.getFoodPrice(), newFoodPrice, 0.1);
    }

    @Test
    public void getHandleDate() {
        assertEquals(orderTicketsInfo.getHandleDate(), handleDate);
    }

    @Test
    public void setHandleDate() {
        String newHandleDate = "666";
        orderTicketsInfo.setHandleDate(newHandleDate);
        assertEquals(orderTicketsInfo.getHandleDate(), newHandleDate);
    }

    @Test
    public void getConsigneeName() {
        assertEquals(orderTicketsInfo.getConsigneeName(), consigneeName);
    }

    @Test
    public void setConsigneeName() {
        String newConsigneeName = "xiaohong";
        orderTicketsInfo.setConsigneeName(newConsigneeName);
        assertEquals(orderTicketsInfo.getConsigneeName(), newConsigneeName);
    }

    @Test
    public void getConsigneePhone() {
        assertEquals(orderTicketsInfo.getConsigneePhone(), consigneePhone);
    }

    @Test
    public void setConsigneePhone() {
        String newConsigneePhone = "10010";
        orderTicketsInfo.setConsigneePhone(newConsigneePhone);
        assertEquals(orderTicketsInfo.getConsigneePhone(), newConsigneePhone);
    }

    @Test
    public void getConsigneeWeight() {
        assertEquals(orderTicketsInfo.getConsigneeWeight(), consigneeWeight, 0.1);
    }

    @Test
    public void setConsigneeWeight() {
        Double newConsigneeWeight = 200.0;
        orderTicketsInfo.setConsigneeWeight(newConsigneeWeight);
        assertEquals(orderTicketsInfo.getConsigneeWeight(), newConsigneeWeight, 0.1);
    }

    @Test
    public void isWithin() {
        assertEquals(orderTicketsInfo.isWithin(), isWithin);
    }

    @Test
    public void setWithin() {
        orderTicketsInfo.setWithin(false);
        assertFalse(orderTicketsInfo.isWithin());
    }

    @Test
    public void getContactsId() {
        assertEquals(orderTicketsInfo.getContactsId(), contactsId);
    }

    @Test
    public void setContactsId() {
        String newContactsId = "999";
        orderTicketsInfo.setContactsId(newContactsId);
        assertEquals(orderTicketsInfo.getContactsId(), newContactsId);
    }

    @Test
    public void getTripId() {
        assertEquals(orderTicketsInfo.getTripId(), tripId);
    }

    @Test
    public void setTripId() {
        String newTripId = "999";
        orderTicketsInfo.setTripId(newTripId);
        assertEquals(orderTicketsInfo.getTripId(), newTripId);
    }

    @Test
    public void getSeatType() {
        assertEquals(orderTicketsInfo.getSeatType(), seatType);
    }

    @Test
    public void setSeatType() {
        int newSeatType = 2;
        orderTicketsInfo.setSeatType(newSeatType);
        assertEquals(orderTicketsInfo.getSeatType(), newSeatType);
    }

    @Test
    public void getDate() {
        assertEquals(orderTicketsInfo.getDate(), date);
    }

    @Test
    public void setDate() {
        Date newDate = new Date(999999);
        orderTicketsInfo.setDate(newDate);
        assertEquals(orderTicketsInfo.getDate(), newDate);
    }

    @Test
    public void getFrom() {
        assertEquals(orderTicketsInfo.getFrom(), from);
    }

    @Test
    public void setFrom() {
        String newFrom = "hunan";
        orderTicketsInfo.setFrom(newFrom);
        assertEquals(orderTicketsInfo.getFrom(), newFrom);
    }

    @Test
    public void getTo() {
        assertEquals(orderTicketsInfo.getTo(), to);
    }

    @Test
    public void setTo() {
        String newTo = "hubei";
        orderTicketsInfo.setTo(newTo);
        assertEquals(orderTicketsInfo.getTo(), newTo);
    }

    @Test
    public void getAssurance() {
        assertEquals(orderTicketsInfo.getAssurance(), assurance);
    }

    @Test
    public void setAssurance() {
        int newAssurance = 9;
        orderTicketsInfo.setAssurance(newAssurance);
        assertEquals(orderTicketsInfo.getAssurance(), newAssurance);
    }
}