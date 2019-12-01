import org.junit.Before;
import org.junit.Test;
import ticketinfo.entity.Trip;
import ticketinfo.entity.TripId;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TripTest {
    private Trip trip;
    private TripId tripId;
    private String trainTypeId;
    private String routeId;
    private String startingStationId;
    private String stationsId;
    private String terminalStationId;
    private Date startingTime;
    private Date endTime;

    @Before
    public void setUp() {
        tripId = new TripId("G7512");
        trainTypeId = "Normal";
        routeId = "NWE";
        startingStationId = "Beijing";
        stationsId = "Tianjing";
        terminalStationId = "Shanghai";
        startingTime = new Date();
        endTime = new Date();
        trip = new Trip();
        trip = new Trip(tripId, trainTypeId, routeId);
        trip = new Trip(tripId, trainTypeId, startingStationId, stationsId, terminalStationId, startingTime, endTime);
        trip.setRouteId(routeId);
    }

    @Test
    public void testID() {
        trip.setTripId(tripId);
        trip.setTrainTypeId(trainTypeId);
        trip.setRouteId(routeId);
        trip.setStartingStationId(startingStationId);
        trip.setStationsId(stationsId);
        trip.setTerminalStationId(terminalStationId);
        trip.setStartingTime(startingTime);
        trip.setEndTime(endTime);
        assertThat(trip.getTripId(), is(tripId));
        assertThat(trip.getTrainTypeId(), is(trainTypeId));
        assertThat(trip.getRouteId(), is(routeId));
        assertThat(trip.getStartingStationId(), is(startingStationId));
        assertThat(trip.getStationsId(), is(stationsId));
        assertThat(trip.getTerminalStationId(), is(terminalStationId));
        assertThat(trip.getStartingTime(), is(startingTime));
        assertThat(trip.getEndTime(), is(endTime));
        tripId = new TripId("D5048");
        trainTypeId = "Expect";
        routeId = "ZXC";
        trip.setTripId(tripId);
        trip.setTrainTypeId(trainTypeId);
        trip.setRouteId(routeId);
        assertThat(trip.getTripId(), is(tripId));
        assertThat(trip.getTrainTypeId(), is(trainTypeId));
        assertThat(trip.getRouteId(), is(routeId));
    }
}