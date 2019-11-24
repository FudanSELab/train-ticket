package travelto.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * TripResponse Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 22, 2019</pre>
 */
public class TripResponseTest {

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
        TripResponse tripResponse = new TripResponse();
        TripId tripId = new TripId();
        tripResponse.setTripId(tripId);
        Assert.assertEquals(tripId, tripResponse.getTripId());
    }

    /**
     * Method: getTrainTypeId()
     */
    @Test
    public void testGetTrainTypeId() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String trainTypeId = "Z";
        tripResponse.setTrainTypeId(trainTypeId);
        Assert.assertEquals(trainTypeId, tripResponse.getTrainTypeId());
    }

    /**
     * Method: setTrainTypeId(String trainTypeId)
     */
    @Test
    public void testSetTrainTypeId() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String trainTypeId = "Z";
        tripResponse.setTrainTypeId(trainTypeId);
        Assert.assertEquals(trainTypeId, tripResponse.getTrainTypeId());
    }

    /**
     * Method: setTripId(TripId tripId)
     */
    @Test
    public void testSetTripId() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        TripId tripId = new TripId();
        tripResponse.setTripId(tripId);
        Assert.assertEquals(tripId, tripResponse.getTripId());
    }

    /**
     * Method: getStartingStation()
     */
    @Test
    public void testGetStartingStation() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String startingStation = "shanghai";
        tripResponse.setStartingStation(startingStation);
        Assert.assertEquals(startingStation, tripResponse.getStartingStation());
    }

    /**
     * Method: setStartingStation(String startingStation)
     */
    @Test
    public void testSetStartingStation() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String startingStation = "shanghai";
        tripResponse.setStartingStation(startingStation);
        Assert.assertEquals(startingStation, tripResponse.getStartingStation());
    }

    /**
     * Method: getTerminalStation()
     */
    @Test
    public void testGetTerminalStation() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String terminalStation = "beijing";
        tripResponse.setTerminalStation(terminalStation);
        Assert.assertEquals(terminalStation, tripResponse.getTerminalStation());
    }

    /**
     * Method: setTerminalStation(String terminalStation)
     */
    @Test
    public void testSetTerminalStation() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String terminalStation = "beijing";
        tripResponse.setTerminalStation(terminalStation);
        Assert.assertEquals(terminalStation, tripResponse.getTerminalStation());
    }

    /**
     * Method: getStartingTime()
     */
    @Test
    public void testGetStartingTime() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        Date val = Calendar.getInstance().getTime();
        tripResponse.setStartingTime(val);
        Assert.assertEquals(val, tripResponse.getStartingTime());
    }

    /**
     * Method: setStartingTime(Date startingTime)
     */
    @Test
    public void testSetStartingTime() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        Date val = Calendar.getInstance().getTime();
        tripResponse.setStartingTime(val);
        Assert.assertEquals(val, tripResponse.getStartingTime());
    }

    /**
     * Method: getEndTime()
     */
    @Test
    public void testGetEndTime() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        Date val = Calendar.getInstance().getTime();
        tripResponse.setEndTime(val);
        Assert.assertEquals(val, tripResponse.getEndTime());
    }

    /**
     * Method: setEndTime(Date endTime)
     */
    @Test
    public void testSetEndTime() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        Date val = Calendar.getInstance().getTime();
        tripResponse.setEndTime(val);
        Assert.assertEquals(val, tripResponse.getEndTime());
    }

    /**
     * Method: getEconomyClass()
     */
    @Test
    public void testGetEconomyClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        int val = 233;
        tripResponse.setEconomyClass(val);
        Assert.assertEquals(val, tripResponse.getEconomyClass());
    }

    /**
     * Method: setEconomyClass(int economyClass)
     */
    @Test
    public void testSetEconomyClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        int val = 233;
        tripResponse.setEconomyClass(val);
        Assert.assertEquals(val, tripResponse.getEconomyClass());
    }

    /**
     * Method: getConfortClass()
     */
    @Test
    public void testGetConfortClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        int val = 234;
        tripResponse.setConfortClass(val);
        Assert.assertEquals(val, tripResponse.getConfortClass());
    }

    /**
     * Method: setConfortClass(int confortClass)
     */
    @Test
    public void testSetConfortClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        int val = 234;
        tripResponse.setConfortClass(val);
        Assert.assertEquals(val, tripResponse.getConfortClass());
    }

    /**
     * Method: getPriceForEconomyClass()
     */
    @Test
    public void testGetPriceForEconomyClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String val = "233";
        tripResponse.setPriceForEconomyClass(val);
        Assert.assertEquals(val, tripResponse.getPriceForEconomyClass());
    }

    /**
     * Method: setPriceForEconomyClass(String priceForEconomyClass)
     */
    @Test
    public void testSetPriceForEconomyClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String val = "233";
        tripResponse.setPriceForEconomyClass(val);
        Assert.assertEquals(val, tripResponse.getPriceForEconomyClass());
    }

    /**
     * Method: getPriceForConfortClass()
     */
    @Test
    public void testGetPriceForConfortClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String val = "234";
        tripResponse.setPriceForConfortClass(val);
        Assert.assertEquals(val, tripResponse.getPriceForConfortClass());
    }

    /**
     * Method: setPriceForConfortClass(String priceForConfortClass)
     */
    @Test
    public void testSetPriceForConfortClass() throws Exception {
//TODO: Test goes here...
        TripResponse tripResponse = new TripResponse();
        String val = "234";
        tripResponse.setPriceForConfortClass(val);
        Assert.assertEquals(val, tripResponse.getPriceForConfortClass());
    }


} 
