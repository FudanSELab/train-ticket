package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.TripAllDetailInfo;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TripAllDetailInfoTest {
    private String tripId;
    private Date travelDate;
    private String from;
    private String to;
    private TripAllDetailInfo tripAllDetailInfo;

    @Before
    public void setUp() {
        tripId = "1";
        travelDate = new Date(111111);
        from = "shanghai";
        to = "beijing";
        tripAllDetailInfo = new TripAllDetailInfo(tripId, travelDate, from, to);
    }

    @Test
    public void getTripId() {
        assertEquals(tripAllDetailInfo.getTripId(), tripId);
    }

    @Test
    public void setTripId() {
        String newId = "2";
        tripAllDetailInfo.setTripId(newId);
        assertEquals(tripAllDetailInfo.getTripId(), newId);
    }

    @Test
    public void getTravelDate() {
        assertEquals(tripAllDetailInfo.getTravelDate(), travelDate);
    }

    @Test
    public void setTravelDate() {
        Date newDate = new Date(222222);
        tripAllDetailInfo.setTravelDate(newDate);
        assertEquals(tripAllDetailInfo.getTravelDate(), newDate);
    }

    @Test
    public void getFrom() {
        assertEquals(tripAllDetailInfo.getFrom(), from);
    }

    @Test
    public void setFrom() {
        String newFrom = "hunan";
        tripAllDetailInfo.setFrom(newFrom);
        assertEquals(tripAllDetailInfo.getFrom(), newFrom);
    }

    @Test
    public void getTo() {
        assertEquals(tripAllDetailInfo.getTo(), to);
    }

    @Test
    public void setTo() {
        String newTo = "hubei";
        tripAllDetailInfo.setTo(newTo);
        assertEquals(tripAllDetailInfo.getTo(), newTo);
    }

    @Test
    public void testDefault() {
        TripAllDetailInfo defaultTripAllDetailInfo = new TripAllDetailInfo();
        assertNull(defaultTripAllDetailInfo.getFrom());
    }
}