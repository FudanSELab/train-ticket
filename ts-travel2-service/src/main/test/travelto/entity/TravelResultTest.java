package travelto.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

/**
 * TravelResult Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TravelResultTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: isStatus()
     */
    @Test
    public void testIsStatus() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        boolean val = true;
        travelResult.setStatus(val);
        Assert.assertEquals(val, travelResult.isStatus());
    }

    /**
     * Method: setStatus(boolean status)
     */
    @Test
    public void testSetStatus() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        boolean val = true;
        travelResult.setStatus(val);
        Assert.assertEquals(val, travelResult.isStatus());
    }

    /**
     * Method: getPercent()
     */
    @Test
    public void testGetPercent() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        double val = 0.5;
        travelResult.setPercent(val);
        Assert.assertEquals(val, travelResult.getPercent(), 0.001);
    }

    /**
     * Method: setPercent(double percent)
     */
    @Test
    public void testSetPercent() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        double val = 0.5;
        travelResult.setPercent(val);
        Assert.assertEquals(val, travelResult.getPercent(), 0.001);
    }

    /**
     * Method: getTrainType()
     */
    @Test
    public void testGetTrainType() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        TrainType val = new TrainType();
        travelResult.setTrainType(val);
        Assert.assertEquals(val, travelResult.getTrainType());
    }

    /**
     * Method: setTrainType(TrainType trainType)
     */
    @Test
    public void testSetTrainType() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        TrainType val = new TrainType();
        travelResult.setTrainType(val);
        Assert.assertEquals(val, travelResult.getTrainType());
    }

    /**
     * Method: getPrices()
     */
    @Test
    public void testGetPrices() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        Map<String, String> val = new HashMap<>();
        travelResult.setPrices(val);
        Assert.assertEquals(val, travelResult.getPrices());
    }

    /**
     * Method: setPrices(Map<String, String> prices)
     */
    @Test
    public void testSetPrices() throws Exception {
//TODO: Test goes here...
        TravelResult travelResult = new TravelResult();
        Map<String, String> val = new HashMap<>();
        travelResult.setPrices(val);
        Assert.assertEquals(val, travelResult.getPrices());
    }


} 
