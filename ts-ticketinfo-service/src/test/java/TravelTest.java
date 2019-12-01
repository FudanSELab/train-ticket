import org.junit.Before;
import org.junit.Test;
import ticketinfo.entity.Travel;
import ticketinfo.entity.Trip;
import ticketinfo.entity.TripId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TravelTest {

    private Travel travelA;
    private Trip trip;
    private String startingPlace;
    private String endPlace;
    private Date departureTime;

    @Before
    public void setUp() throws ParseException {
        trip = new Trip(new TripId("G7512"), "Normal", "NWE");
        startingPlace = "Shanghai Hongqiao";
        endPlace = "Shaoxingbei";
        departureTime = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse("2019-09-03 13:56:03");
        travelA = new Travel();
    }

    @Test
    public void testID() {
        travelA.setTrip(trip);
        travelA.setStartingPlace(startingPlace);
        travelA.setEndPlace(endPlace);
        travelA.setDepartureTime(departureTime);
        assertThat(travelA.getTrip(), is(trip));
        assertThat(travelA.getStartingPlace(), is(startingPlace));
        assertThat(travelA.getEndPlace(), is(endPlace));
        assertThat(travelA.getDepartureTime(), is(departureTime));
    }
}