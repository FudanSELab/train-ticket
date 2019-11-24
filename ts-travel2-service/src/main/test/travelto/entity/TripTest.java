package travelto.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Trip Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TripTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getTripId()
     */
    @Test
    public void testGetTripId() throws Exception {
//TODO: Test goes here...
        Trip trip = new Trip();
        TripId val = new TripId();
        trip.setTripId(val);
        Assert.assertEquals(val, trip.getTripId());
    }

    /**
     * Method: setTripId(TripId tripId)
     */
    @Test
    public void testSetTripId() throws Exception {
//TODO: Test goes here...
        Trip trip = new Trip();
        TripId val = new TripId();
        trip.setTripId(val);
        Assert.assertEquals(val, trip.getTripId());
    }
}
