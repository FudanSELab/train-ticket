package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.TripId;
import preserveother.entity.TripResponse;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TripResponseTest {
    private TripId tripId;
    private String startingStation;
    private String terminalStation;
    private Date startingTime;
    private Date endTime;
    private int economyClass;
    private int confortClass;
    private TripResponse tripResponse;

    @Before
    public void setUp() {
        tripId = new TripId("G100");
        startingStation = "shanghai";
        terminalStation = "beijing";
        startingTime = new Date(999999);
        endTime = new Date(666666);
        economyClass = 1;
        confortClass = 1;
        tripResponse = new TripResponse(tripId, startingStation, terminalStation, startingTime, endTime, economyClass, confortClass);
    }

    @Test
    public void getTripId() {
        assertEquals(tripResponse.getTripId(), tripId);
    }

    @Test
    public void setTripId() {
        TripId newTripId = new TripId("K1048");
        tripResponse.setTripId(newTripId);
        assertEquals(tripResponse.getTripId(), newTripId);
    }

    @Test
    public void getStartingStation() {
        assertEquals(tripResponse.getStartingStation(), startingStation);
    }

    @Test
    public void setStartingStation() {
        String newStartingStation = "hunan";
        tripResponse.setStartingStation(newStartingStation);
        assertEquals(tripResponse.getStartingStation(), newStartingStation);
    }

    @Test
    public void getTerminalStation() {
        assertEquals(tripResponse.getTerminalStation(), terminalStation);
    }

    @Test
    public void setTerminalStation() {
        String newTerminalStation = "hubei";
        tripResponse.setTerminalStation(newTerminalStation);
        assertEquals(tripResponse.getTerminalStation(), newTerminalStation);
    }

    @Test
    public void getStartingTime() {
        assertEquals(tripResponse.getStartingTime(), startingTime);
    }

    @Test
    public void setStartingTime() {
        Date newStartingTime = new Date(111111);
        tripResponse.setStartingTime(newStartingTime);
        assertEquals(tripResponse.getStartingTime(), newStartingTime);
    }

    @Test
    public void getEndTime() {
        assertEquals(tripResponse.getEndTime(), endTime);
    }

    @Test
    public void setEndTime() {
        Date newEndTime = new Date(222222);
        tripResponse.setEndTime(newEndTime);
        assertEquals(tripResponse.getEndTime(), newEndTime);
    }

    @Test
    public void getEconomyClass() {
        assertEquals(tripResponse.getEconomyClass(), economyClass);
    }

    @Test
    public void setEconomyClass() {
        int newEconomyClass = 2;
        tripResponse.setEconomyClass(newEconomyClass);
        assertEquals(tripResponse.getEconomyClass(), newEconomyClass);
    }

    @Test
    public void getConfortClass() {
        assertEquals(tripResponse.getConfortClass(), confortClass);
    }

    @Test
    public void setConfortClass() {
        int newConfortClass = 2;
        tripResponse.setConfortClass(newConfortClass);
        assertEquals(tripResponse.getConfortClass(), newConfortClass);
    }

    @Test
    public void testToString() {
        assertNotNull(tripResponse.toString());
    }
}