package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* Route Tester. 
* 
* @author <Authors name> 
* @since <pre>11‘¬ 22, 2019</pre> 
* @version 1.0 
*/ 
public class RouteTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getId() 
* 
*/ 
@Test
public void testGetId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getId(),myid);
} 

/** 
* 
* Method: setId(String id) 
* 
*/ 
@Test
public void testSetId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getId(),myid);
} 

/** 
* 
* Method: getStations() 
* 
*/ 
@Test
public void testGetStations() throws Exception { 
//TODO: Test goes here...
    Route ro = new Route();
    List<String> list = new ArrayList<String>();
    list.add("beijing");
    list.add("shanghai");
    list.add("guangzhou");
    ro.setStations(list);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getStations(),list);
} 

/** 
* 
* Method: setStations(List<String> stations) 
* 
*/ 
@Test
public void testSetStations() throws Exception { 
//TODO: Test goes here...
    Route ro = new Route();
    List<String> list = new ArrayList<String>();
    list.add("beijing");
    list.add("shanghai");
    list.add("guangzhou");
    ro.setStations(list);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getStations(),list);
} 

/** 
* 
* Method: getDistances() 
* 
*/ 
@Test
public void testGetDistances() throws Exception { 
//TODO: Test goes here...
    Route ro = new Route();
    List<Integer> list = new ArrayList<Integer>();
    list.add(100);
    list.add(101);
    list.add(102);
    ro.setDistances(list);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getDistances(),list);
} 

/** 
* 
* Method: setDistances(List<Integer> distances) 
* 
*/ 
@Test
public void testSetDistances() throws Exception { 
//TODO: Test goes here...
    Route ro = new Route();
    List<Integer> list = new ArrayList<Integer>();
    list.add(100);
    list.add(101);
    list.add(102);
    ro.setDistances(list);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getDistances(),list);
} 

/** 
* 
* Method: getStartStationId() 
* 
*/ 
@Test
public void testGetStartStationId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setStartStationId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getStartStationId(),myid);

} 

/** 
* 
* Method: setStartStationId(String startStationId) 
* 
*/ 
@Test
public void testSetStartStationId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setStartStationId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getStartStationId(),myid);
} 

/** 
* 
* Method: getTerminalStationId() 
* 
*/ 
@Test
public void testGetTerminalStationId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setTerminalStationId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getTerminalStationId(),myid);
} 

/** 
* 
* Method: setTerminalStationId(String terminalStationId) 
* 
*/ 
@Test
public void testSetTerminalStationId() throws Exception { 
//TODO: Test goes here...
    Route ro;
    ro = new Route();
    String myid="myid";
    ro.setTerminalStationId(myid);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(ro.getTerminalStationId(),myid);
} 


} 
