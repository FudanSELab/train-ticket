package travelto.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
* TravelInfo Tester. 
* 
* @author <Authors name> 
* @since <pre>11�� 23, 2019</pre> 
* @version 1.0 
*/ 
public class TravelInfoTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTripId() 
* 
*/ 
@Test
public void testGetTripId() throws Exception { 
//TODO: Test goes here...
    TravelInfo tb=new TravelInfo();
    String var="myid";
    tb.setTripId(var);
    //�����﷨
    Assert.assertEquals(tb.getTripId(),var);
} 

/** 
* 
* Method: setTripId(String tripId) 
* 
*/ 
@Test
public void testSetTripId() throws Exception { 
//TODO: Test goes here...
    TravelInfo tb=new TravelInfo();
    String var="myid";
    tb.setTripId(var);
    //�����﷨
    Assert.assertEquals(tb.getTripId(),var);
} 


} 
