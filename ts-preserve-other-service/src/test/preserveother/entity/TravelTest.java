package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Travel;
import preserveother.entity.Trip;
import preserveother.entity.TripId;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TravelTest {
    private Trip trip;
    private String startingPlace;
    private String endPlace;
    private Date departureTime;
    private Travel travel;

    @Before
    public void setUp() {
        trip = new Trip(new TripId("G100"), "train", "shanghai", "shanghai-henan-beijing", "beijing", new Date(111111), new Date(222222));
        startingPlace = "shanghai";
        endPlace = "beijing";
        departureTime = new Date(888888);
        travel = new Travel(trip, startingPlace, endPlace, departureTime);
    }

    @Test
    public void getTrip() {
        assertEquals(travel.getTrip(), trip);
    }

    @Test
    public void setTrip() {
        Trip newTrip = new Trip(new TripId("K1048"), "fast", "shanghai", "shanghai-henan-beijing", "beijing", new Date(111111), new Date(222222));
        travel.setTrip(newTrip);
        assertEquals(travel.getTrip(), newTrip);
    }

    @Test
    public void getStartingPlace() {
        assertEquals(travel.getStartingPlace(), startingPlace);
    }

    @Test
    public void setStartingPlace() {
        String newStartingPlace = "hunan";
        travel.setStartingPlace(newStartingPlace);
        assertEquals(travel.getStartingPlace(), newStartingPlace);
    }

    @Test
    public void getEndPlace() {
        assertEquals(travel.getEndPlace(), endPlace);
    }

    @Test
    public void setEndPlace() {
        String newEndPlace = "hubei";
        travel.setEndPlace(newEndPlace);
        assertEquals(travel.getEndPlace(), newEndPlace);
    }

    @Test
    public void getDepartureTime() {
        assertEquals(travel.getDepartureTime(), departureTime);
    }

    @Test
    public void setDepartureTime() {
        Date newDepatureTime = new Date(99999);
        travel.setDepartureTime(newDepatureTime);
        assertEquals(travel.getDepartureTime(), newDepatureTime);
    }

    @Test
    public void testDefault() {
        assertNull(new Travel().getStartingPlace());
    }
}