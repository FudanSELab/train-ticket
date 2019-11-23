package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Calendar;
import java.util.Date;

/** 
* TravelBase Tester. 
* 
* @author <Authors name> 
* @since <pre>11‘¬ 23, 2019</pre> 
* @version 1.0 
*/ 
public class TravelBaseTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTrainTypeId() 
* 
*/ 
@Test
public void testGetTrainTypeId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setTrainTypeId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getTrainTypeId(),var);
} 

/** 
* 
* Method: setTrainTypeId(String trainTypeId) 
* 
*/ 
@Test
public void testSetTrainTypeId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setTrainTypeId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getTrainTypeId(),var);
} 

/** 
* 
* Method: getStartingStationId() 
* 
*/ 
@Test
public void testGetStartingStationId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setStartingStationId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getStartingStationId(),var);
} 

/** 
* 
* Method: setStartingStationId(String startingStationId) 
* 
*/ 
@Test
public void testSetStartingStationId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setStartingStationId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getStartingStationId(),var);
} 

/** 
* 
* Method: getStationsId() 
* 
*/ 
@Test
public void testGetStationsId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setStationsId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getStationsId(),var);
} 

/** 
* 
* Method: setStationsId(String stationsId) 
* 
*/ 
@Test
public void testSetStationsId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setStationsId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getStationsId(),var);
} 

/** 
* 
* Method: getTerminalStationId() 
* 
*/ 
@Test
public void testGetTerminalStationId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setTerminalStationId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getTerminalStationId(),var);
} 

/** 
* 
* Method: setTerminalStationId(String terminalStationId) 
* 
*/ 
@Test
public void testSetTerminalStationId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String var="myid";
    tb.setTerminalStationId(var);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(tb.getTerminalStationId(),var);
} 

/** 
* 
* Method: getStartingTime() 
* 
*/ 
@Test
public void testGetStartingTime() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    Date val = Calendar.getInstance().getTime();
    tb.setStartingTime(val);
    Assert.assertEquals(tb.getStartingTime(),val);
} 

/** 
* 
* Method: setStartingTime(Date startingTime) 
* 
*/ 
@Test
public void testSetStartingTime() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    Date val = Calendar.getInstance().getTime();
    tb.setStartingTime(val);
    Assert.assertEquals(tb.getStartingTime(),val);
} 

/** 
* 
* Method: getEndTime() 
* 
*/ 
@Test
public void testGetEndTime() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    Date val = Calendar.getInstance().getTime();
    tb.setEndTime(val);
    Assert.assertEquals(tb.getEndTime(),val);
} 

/** 
* 
* Method: setEndTime(Date endTime) 
* 
*/ 
@Test
public void testSetEndTime() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    Date val = Calendar.getInstance().getTime();
    tb.setEndTime(val);
    Assert.assertEquals(tb.getEndTime(),val);
} 

/** 
* 
* Method: getRouteId() 
* 
*/ 
@Test
public void testGetRouteId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String myid="myid";
    tb.setRouteId(myid);
    Assert.assertEquals(tb.getRouteId(),myid);
} 

/** 
* 
* Method: setRouteId(String routeId) 
* 
*/ 
@Test
public void testSetRouteId() throws Exception { 
//TODO: Test goes here...
    TravelBase tb=new TravelBase();
    String myid="myid";
    tb.setRouteId(myid);
    Assert.assertEquals(tb.getRouteId(),myid);
} 


} 
