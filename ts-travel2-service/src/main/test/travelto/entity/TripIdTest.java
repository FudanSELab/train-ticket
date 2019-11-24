package travelto.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * TripId Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TripIdTest {

    @Before
    public void before() throws Exception {
        TripId ti =new TripId("Z220");
        TripId ti2 =new TripId("T220");
        TripId ti3 =new TripId("K220");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getType()
     */
    @Test
    public void testGetType() throws Exception {
//TODO: Test goes here...
        TripId tripId = new TripId();
        Type val = Type.Z;
        tripId.setType(val);
        Assert.assertEquals(val, tripId.getType());
    }

    /**
     * Method: setType(Type type)
     */
    @Test
    public void testSetType() throws Exception {
//TODO: Test goes here...
        TripId tripId = new TripId();
        Type val = Type.Z;
        tripId.setType(val);
        Assert.assertEquals(val, tripId.getType());
    }

    /**
     * Method: getNumber()
     */
    @Test
    public void testGetNumber() throws Exception {
//TODO: Test goes here...
        TripId tripId = new TripId();
        String val = "233";
        tripId.setNumber(val);
        Assert.assertEquals(val, tripId.getNumber());
    }

    /**
     * Method: setNumber(String number)
     */
    @Test
    public void testSetNumber() throws Exception {
//TODO: Test goes here...
        TripId tripId = new TripId();
        String val = "233";
        tripId.setNumber(val);
        Assert.assertEquals(val, tripId.getNumber());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
//TODO: Test goes here...
        TripId tripId = new TripId();
        Type val1 = Type.Z;
        String val2 = "233";
        tripId.setType(val1);
        tripId.setNumber(val2);
        Assert.assertEquals("Z" + val2, tripId.toString());
    }


} 
