package travelto.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
* Travel Tester. 
* 
* @author <Authors name> 
* @since <pre>11�� 23, 2019</pre> 
* @version 1.0 
*/ 
public class TravelTest { 

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
    Travel tr=new Travel();
    Trip t=new Trip();
    tr.setTrip(t);
    //�����﷨
    Assert.assertEquals(tr.getTrip(),t);
} 

/** 
* 
* Method: setTrip(Trip trip) 
* 
*/ 
@Test
public void testSetTrip() throws Exception { 
//TODO: Test goes here...
    Travel tr=new Travel();
    Trip t=new Trip();
    tr.setTrip(t);
    //�����﷨
    Assert.assertEquals(tr.getTrip(),t);
} 


} 
