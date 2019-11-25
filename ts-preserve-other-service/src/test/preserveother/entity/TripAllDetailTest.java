package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Trip;
import preserveother.entity.TripAllDetail;
import preserveother.entity.TripId;
import preserveother.entity.TripResponse;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TripAllDetailTest {
    private boolean status;
    private String message;
    private TripResponse tripResponse;
    private Trip trip;
    private TripAllDetail tripAllDetail;

    @Before
    public void setUp() throws Exception {
        status = true;
        message = "hello";
        tripResponse = new TripResponse(new TripId("G100"), "shanghai", "beijing", new Date(999999), new Date(666666), 1, 1);
        trip = new Trip(new TripId("G100"), "train", "shanghai", "shanghai-henan-beijing", "beijing", new Date(111111), new Date(222222));
        tripAllDetail = new TripAllDetail();
    }

    @Test
    public void isStatus() {
        assertFalse(tripAllDetail.isStatus());
    }

    @Test
    public void setStatus() {
        tripAllDetail.setStatus(status);
        assertEquals(tripAllDetail.isStatus(), status);
    }

    @Test
    public void getMessage() {
        assertNull(tripAllDetail.getMessage());
    }

    @Test
    public void setMessage() {
        String newMessage = "world";
        tripAllDetail.setMessage(newMessage);
        assertEquals(tripAllDetail.getMessage(), newMessage);
    }

    @Test
    public void getTripResponse() {
        assertNull(tripAllDetail.getTripResponse());
    }

    @Test
    public void setTripResponse() {
        tripAllDetail.setTripResponse(tripResponse);
        assertEquals(tripAllDetail.getTripResponse(), tripResponse);
    }

    @Test
    public void getTrip() {
        assertNull(tripAllDetail.getTrip());
    }

    @Test
    public void setTrip() {
        tripAllDetail.setTrip(trip);
        assertEquals(tripAllDetail.getTrip(), trip);
    }

    @Test
    public void testToString() {
        assertNotNull(tripAllDetail.toString());
    }
}