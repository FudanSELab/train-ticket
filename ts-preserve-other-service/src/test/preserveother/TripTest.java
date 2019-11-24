package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Trip;
import preserveother.entity.TripId;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TripTest {
    private TripId tripId;
    private String trainTypeId;
    private String startingStationId;
    private String stationsId;
    private String terminalStationId;
    private Date startingTime;
    private Date endTime;
    private Trip trip;

    @Before
    public void setUp() {
        tripId = new TripId("G100");
        trainTypeId = "train";
        startingStationId = "shanghai";
        stationsId = "shanghai-henan-beijing";
        terminalStationId = "beijing";
        startingTime = new Date(111111);
        endTime = new Date(222222);
        trip = new Trip(tripId, trainTypeId, startingStationId, stationsId, terminalStationId, startingTime, endTime);
    }

    @Test
    public void getTripId() {
        assertEquals(trip.getTripId(), tripId);
    }

    @Test
    public void setTripId() {
        TripId newTripId = new TripId("K1048");
        trip.setTripId(newTripId);
        assertEquals(trip.getTripId(), newTripId);
    }

    @Test
    public void getTrainTypeId() {
        assertEquals(trip.getTrainTypeId(), trainTypeId);
    }

    @Test
    public void setTrainTypeId() {
        String newTrainTypeId = "fast";
        trip.setTrainTypeId(newTrainTypeId);
        assertEquals(trip.getTrainTypeId(), newTrainTypeId);
    }

    @Test
    public void getStartingStationId() {
        assertEquals(trip.getStartingStationId(), startingStationId);
    }

    @Test
    public void setStartingStationId() {
        String newStartingStatinId = "hunan";
        trip.setStartingStationId(newStartingStatinId);
        assertEquals(trip.getStartingStationId(), newStartingStatinId);
    }

    @Test
    public void getStationsId() {
        assertEquals(trip.getStationsId(), stationsId);
    }

    @Test
    public void setStationsId() {
        String newStationsId = "hunan-changsha";
        trip.setStationsId(newStationsId);
        assertEquals(trip.getStationsId(), newStationsId);
    }

    @Test
    public void getTerminalStationId() {
        assertEquals(trip.getTerminalStationId(), terminalStationId);
    }

    @Test
    public void setTerminalStationId() {
        String newTerminalStationId = "hubei";
        trip.setTerminalStationId(newTerminalStationId);
        assertEquals(trip.getTerminalStationId(), newTerminalStationId);
    }

    @Test
    public void getStartingTime() {
        assertEquals(trip.getStartingTime(), startingTime);
    }

    @Test
    public void setStartingTime() {
        Date newStartingTime = new Date(666666);
        trip.setStartingTime(newStartingTime);
        assertEquals(trip.getStartingTime(), newStartingTime);
    }

    @Test
    public void getEndTime() {
        assertEquals(trip.getEndTime(), endTime);
    }

    @Test
    public void setEndTime() {
        Date newEndTime = new Date(7777777);
        trip.setEndTime(newEndTime);
        assertEquals(trip.getEndTime(), newEndTime);
    }

    @Test
    public void getRouteId() {
        assertNull(trip.getRouteId());
    }

    @Test
    public void setRouteId() {
        trip.setRouteId("8");
        assertEquals(trip.getRouteId(), "8");
    }

    @Test
    public void testDefault() {
        Trip defaultTrip = new Trip(trainTypeId, startingStationId, terminalStationId, startingTime, endTime);
        assertEquals(defaultTrip.getTrainTypeId(), trainTypeId);
        assertEquals(defaultTrip.getStartingStationId(), startingStationId);
        assertEquals(defaultTrip.getTerminalStationId(), terminalStationId);
        assertEquals(defaultTrip.getStartingTime(), startingTime);
        assertEquals(defaultTrip.getEndTime(), endTime);
    }
}