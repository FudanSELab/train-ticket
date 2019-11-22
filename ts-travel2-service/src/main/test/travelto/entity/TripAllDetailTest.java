package travelto.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * TripAllDetail Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TripAllDetailTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getTrip()
     */
    @Test
    public void testGetTrip() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        Trip val = new Trip();
        tripAllDetail.setTrip(val);
        Assert.assertEquals(val, tripAllDetail.getTrip());
    }

    /**
     * Method: setTrip(Trip trip)
     */
    @Test
    public void testSetTrip() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        Trip val = new Trip();
        tripAllDetail.setTrip(val);
        Assert.assertEquals(val, tripAllDetail.getTrip());
    }

    /**
     * Method: isStatus()
     */
    @Test
    public void testIsStatus() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        boolean val = true;
        tripAllDetail.setStatus(val);
        Assert.assertEquals(val, tripAllDetail.isStatus());
    }

    /**
     * Method: setStatus(boolean status)
     */
    @Test
    public void testSetStatus() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        boolean val = true;
        tripAllDetail.setStatus(val);
        Assert.assertEquals(val, tripAllDetail.isStatus());
    }

    /**
     * Method: getMessage()
     */
    @Test
    public void testGetMessage() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        String val = "hhh";
        tripAllDetail.setMessage(val);
        Assert.assertEquals(val, tripAllDetail.getMessage());
    }

    /**
     * Method: setMessage(String message)
     */
    @Test
    public void testSetMessage() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        String val = "hhh";
        tripAllDetail.setMessage(val);
        Assert.assertEquals(val, tripAllDetail.getMessage());
    }

    /**
     * Method: getTripResponse()
     */
    @Test
    public void testGetTripResponse() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        TripResponse val = new TripResponse();
        tripAllDetail.setTripResponse(val);
        Assert.assertEquals(val, tripAllDetail.getTripResponse());
    }

    /**
     * Method: setTripResponse(TripResponse tripResponse)
     */
    @Test
    public void testSetTripResponse() throws Exception {
//TODO: Test goes here...
        TripAllDetail tripAllDetail = new TripAllDetail();
        TripResponse val = new TripResponse();
        tripAllDetail.setTripResponse(val);
        Assert.assertEquals(val, tripAllDetail.getTripResponse());
    }


} 
