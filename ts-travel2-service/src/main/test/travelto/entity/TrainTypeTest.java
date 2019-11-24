package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* TrainType Tester. 
* 
* @author <Authors name> 
* @since <pre>11ÔÂ 23, 2019</pre> 
* @version 1.0 
*/ 
public class TrainTypeTest { 

@Before
public void before() throws Exception {
    TrainType tt= new TrainType("myid",1,1,200);
    TrainType tt2= new TrainType("myid",1,1);
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
    TrainType tt= new TrainType();
    String var ="myid";
    tt.setId(var);
    Assert.assertEquals(tt.getId(),var);

} 

/** 
* 
* Method: setId(String id) 
* 
*/ 
@Test
public void testSetId() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    String var ="myid";
    tt.setId(var);
    Assert.assertEquals(tt.getId(),var);
} 

/** 
* 
* Method: getEconomyClass() 
* 
*/ 
@Test
public void testGetEconomyClass() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =1;
    tt.setEconomyClass(var);
    Assert.assertEquals(tt.getEconomyClass(),var);
} 

/** 
* 
* Method: setEconomyClass(int economyClass) 
* 
*/ 
@Test
public void testSetEconomyClass() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =1;
    tt.setEconomyClass(var);
    Assert.assertEquals(tt.getEconomyClass(),var);
} 

/** 
* 
* Method: getConfortClass() 
* 
*/ 
@Test
public void testGetConfortClass() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =1;
    tt.setConfortClass(var);
    Assert.assertEquals(tt.getConfortClass(),var);
} 

/** 
* 
* Method: setConfortClass(int confortClass) 
* 
*/ 
@Test
public void testSetConfortClass() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =1;
    tt.setConfortClass(var);
    Assert.assertEquals(tt.getConfortClass(),var);
} 

/** 
* 
* Method: getAverageSpeed() 
* 
*/ 
@Test
public void testGetAverageSpeed() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =200;
    tt.setAverageSpeed(var);
    Assert.assertEquals(tt.getAverageSpeed(),var);

} 

/** 
* 
* Method: setAverageSpeed(int averageSpeed) 
* 
*/ 
@Test
public void testSetAverageSpeed() throws Exception { 
//TODO: Test goes here...
    TrainType tt= new TrainType();
    int var =200;
    tt.setAverageSpeed(var);
    Assert.assertEquals(tt.getAverageSpeed(),var);
} 


} 
