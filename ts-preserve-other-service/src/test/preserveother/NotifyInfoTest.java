package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.NotifyInfo;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class NotifyInfoTest {
    private String testEmail;
    private String testOrderNumber;
    private String testUsername;
    private String testStartingPlace;
    private String testEndPlace;
    private String testStartingTime;
    private String testDate;
    private String testSeatClass;
    private String testSeatNumber;
    private String testPrice;

    private NotifyInfo testNotifyInfo;

    @Before
    public void setUp() {
        testEmail = "10001@qq.com";
        testOrderNumber = "10086";
        testUsername = "mht";
        testStartingPlace = "shanghai";
        testEndPlace = "beijing";
        testStartingTime = "20:00";
        testDate = "2019-11-23";
        testSeatClass = "business";
        testSeatNumber = "1";
        testPrice = "500";
        testNotifyInfo = new NotifyInfo(testEmail, testOrderNumber, testUsername, testStartingPlace, testEndPlace, testStartingTime, testDate, testSeatClass, testSeatNumber, testPrice);
    }

    @Test
    public void getEmail() {
        assertEquals(testNotifyInfo.getEmail(), testEmail);
    }

    @Test
    public void setEmail() {
        String newEmail = "10002@qq.com";
        testNotifyInfo.setEmail(newEmail);
        assertEquals(testNotifyInfo.getEmail(), newEmail);
    }

    @Test
    public void getOrderNumber() {
        assertEquals(testNotifyInfo.getOrderNumber(), testOrderNumber);
    }

    @Test
    public void setOrderNumber() {
        String newOrderNumber = "1008611";
        testNotifyInfo.setOrderNumber(newOrderNumber);
        assertEquals(testNotifyInfo.getOrderNumber(), newOrderNumber);
    }

    @Test
    public void getUsername() {
        assertEquals(testNotifyInfo.getUsername(), testUsername);
    }

    @Test
    public void setUsername() {
        String newUsername = "xiaoming";
        testNotifyInfo.setUsername(newUsername);
        assertEquals(testNotifyInfo.getUsername(), newUsername);
    }

    @Test
    public void getStartingPlace() {
        assertEquals(testNotifyInfo.getStartingPlace(), testStartingPlace);
    }

    @Test
    public void setStartingPlace() {
        String newStartingPlace = "beijing";
        testNotifyInfo.setStartingPlace(newStartingPlace);
        assertEquals(testNotifyInfo.getStartingPlace(), newStartingPlace);
    }

    @Test
    public void getEndPlace() {
        assertEquals(testNotifyInfo.getEndPlace(), testEndPlace);
    }

    @Test
    public void setEndPlace() {
        String newEndPlace = "shanghai";
        testNotifyInfo.setEndPlace(newEndPlace);
        assertEquals(testNotifyInfo.getEndPlace(), newEndPlace);
    }

    @Test
    public void getDate() {
        assertEquals(testNotifyInfo.getDate(), testDate);
    }

    @Test
    public void setDate() {
        String newDate = "2019-11-28";
        testNotifyInfo.setDate(newDate);
        assertEquals(testNotifyInfo.getDate(), newDate);
    }

    @Test
    public void getSeatClass() {
        assertEquals(testNotifyInfo.getSeatClass(), testSeatClass);
    }

    @Test
    public void setSeatClass() {
        String newSeatClass = "vip";
        testNotifyInfo.setSeatClass(newSeatClass);
        assertEquals(testNotifyInfo.getSeatClass(), newSeatClass);
    }

    @Test
    public void getSeatNumber() {
        assertEquals(testNotifyInfo.getSeatNumber(), testSeatNumber);
    }

    @Test
    public void setSeatNumber() {
        String newSeatNumber = "2";
        testNotifyInfo.setSeatNumber(newSeatNumber);
        assertEquals(testNotifyInfo.getSeatNumber(), newSeatNumber);
    }

    @Test
    public void getPrice() {
        assertEquals(testNotifyInfo.getPrice(), testPrice);
    }

    @Test
    public void setPrice() {
        String newPrice = "800";
        testNotifyInfo.setPrice(newPrice);
        assertEquals(testNotifyInfo.getPrice(), newPrice);
    }

    @Test
    public void getStartingTime() {
        assertEquals(testNotifyInfo.getStartingTime(), testStartingTime);
    }

    @Test
    public void setStartingTime() {
        String newStartingTime = "21:00";
        testNotifyInfo.setStartingTime(newStartingTime);
        assertEquals(testNotifyInfo.getStartingTime(), newStartingTime);
    }

    @Test
    public void testDefault() {
        assertNull(new NotifyInfo().getPrice());
    }
}