package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* AdminTrip Tester. 
* 
* @author <Authors name> 
* @since <pre>11�� 22, 2019</pre> 
* @version 1.0 
*/ 
public class AdminTripTest { 

@Before
public void before() throws Exception {
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTrip() 
* 
*/ 
@Test
public void testGetTrip() throws Exception { 
//TODO: Test goes here...
    AdminTrip at;
    at = new AdminTrip();
    Trip t=new Trip();
    at.setTrip(t);
    //断言语法
    Assert.assertEquals(at.getTrip(),t);
} 

/** 
* 
* Method: setTrip(Trip trip) 
* 
*/ 
@Test
public void testSetTrip() throws Exception { 
//TODO: Test goes here...
    AdminTrip at;
    at = new AdminTrip();
    Trip t=new Trip();
    at.setTrip(t);
    //断言语法
    Assert.assertEquals(at.getTrip(),t);
} 

/** 
* 
* Method: getTrainType() 
* 
*/ 
@Test
public void testGetTrainType() throws Exception {
    AdminTrip at;
    at = new AdminTrip();
    TrainType t=new TrainType();
    at.setTrainType(t);
    //断言语法
    Assert.assertEquals(at.getTrainType(),t);

//TODO: Test goes here... 
} 

/** 
* 
* Method: setTrainType(TrainType trainType) 
* 
*/ 
@Test
public void testSetTrainType() throws Exception { 
//TODO: Test goes here...
    AdminTrip at;
    at = new AdminTrip();
    TrainType t=new TrainType();
    at.setTrainType(t);
    //断言语法
    Assert.assertEquals(at.getTrainType(),t);

} 

/** 
* 
* Method: getRoute() 
* 
*/ 
@Test
public void testGetRoute() throws Exception {
    AdminTrip at;
    at = new AdminTrip();
    Route t=new Route();
    at.setRoute(t);
    //断言语法
    Assert.assertEquals(at.getRoute(),t);
} 

/** 
* 
* Method: setRoute(Route route) 
* 
*/ 
@Test
public void testSetRoute() throws Exception { 
//TODO: Test goes here...
    AdminTrip at;
    at = new AdminTrip();
    Route t=new Route();
    at.setRoute(t);
    //断言语法
    Assert.assertEquals(at.getRoute(),t);
} 


} 
