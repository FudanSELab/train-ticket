package travelto.entity;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import travelto.entity.TripBase;

import java.util.Calendar;
import java.util.Date;

/**
 * TripBase Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TripBaseTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getStartingPlace()
     */
    @Test
    public void testGetStartingPlace() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        String val = "shanghai";
        tripBase.startingPlace = val;
        Assert.assertEquals(val, tripBase.getStartingPlace());
    }

    /**
     * Method: setStartingPlace(String startingPlace)
     */
    @Test
    public void testSetStartingPlace() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        String val = "shanghai";
        tripBase.setStartingPlace(val);
        Assert.assertEquals(val, tripBase.startingPlace);
    }

    /**
     * Method: getEndPlace()
     */
    @Test
    public void testGetEndPlace() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        String val = "beijing";
        tripBase.endPlace = val;
        Assert.assertEquals(val, tripBase.getEndPlace());
    }

    /**
     * Method: setEndPlace(String endPlace)
     */
    @Test
    public void testSetEndPlace() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        String val = "beijing";
        tripBase.setEndPlace(val);
        Assert.assertEquals(val, tripBase.endPlace);
    }

    /**
     * Method: getDepartureTime()
     */
    @Test
    public void testGetDepartureTime() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        Date val = Calendar.getInstance().getTime();
        tripBase.departureTime = val;
        Assert.assertEquals(val, tripBase.getDepartureTime());
    }

    /**
     * Method: setDepartureTime(Date departureTime)
     */
    @Test
    public void testSetDepartureTime() throws Exception {
//TODO: Test goes here...
        TripBase tripBase = new TripBase();
        Date val = Calendar.getInstance().getTime();
        tripBase.setDepartureTime(val);
        Assert.assertEquals(val, tripBase.departureTime);
    }


} 
