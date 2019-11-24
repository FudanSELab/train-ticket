package travelto.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * TripAllDetailInfo Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 22, 2019</pre>
 */
public class TripAllDetailInfoTest {

    @Before
    public void before() throws Exception {
        TripAllDetailInfo val=new TripAllDetailInfo();
        Assert.assertEquals(val.toString(), "TripAllDetailInfo(tripId=null, travelDate=null, from=null, to=null)");
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
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "233";
        tripAllDetailInfo.setTripId(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTripId());
    }

    /**
     * Method: setTripId(String tripId)
     */
    @Test
    public void testSetTripId() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "233";
        tripAllDetailInfo.setTripId(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTripId());
    }

    /**
     * Method: getTravelDate()
     */
    @Test
    public void testGetTravelDate() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        Date val = Calendar.getInstance().getTime();
        tripAllDetailInfo.setTravelDate(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTravelDate());
    }

    /**
     * Method: setTravelDate(Date travelDate)
     */
    @Test
    public void testSetTravelDate() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        Date val = Calendar.getInstance().getTime();
        tripAllDetailInfo.setTravelDate(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTravelDate());
    }

    /**
     * Method: getFrom()
     */
    @Test
    public void testGetFrom() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "shanghai";
        tripAllDetailInfo.setFrom(val);
        Assert.assertEquals(val, tripAllDetailInfo.getFrom());
    }

    /**
     * Method: setFrom(String from)
     */
    @Test
    public void testSetFrom() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "shanghai";
        tripAllDetailInfo.setFrom(val);
        Assert.assertEquals(val, tripAllDetailInfo.getFrom());
    }

    /**
     * Method: getTo()
     */
    @Test
    public void testGetTo() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "shanghai";
        tripAllDetailInfo.setTo(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTo());
    }

    /**
     * Method: setTo(String to)
     */
    @Test
    public void testSetTo() throws Exception {
//TODO: Test goes here...
        TripAllDetailInfo tripAllDetailInfo = new TripAllDetailInfo();
        String val = "shanghai";
        tripAllDetailInfo.setTo(val);
        Assert.assertEquals(val, tripAllDetailInfo.getTo());
    }


} 
