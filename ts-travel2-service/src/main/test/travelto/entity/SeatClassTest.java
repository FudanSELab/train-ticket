package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* SeatClass Tester. 
* 
* @author <Authors name> 
* @since <pre>11‘¬ 22, 2019</pre> 
* @version 1.0 
*/ 
public class SeatClassTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getCode() 
* 
*/ 
@Test
public void testGetCode() throws Exception { 
//TODO: Test goes here...
    SeatClass sc = SeatClass.FIRSTCLASS;
    //∂œ—‘”Ô∑®
    Assert.assertEquals(2,sc.getCode());

} 

/** 
* 
* Method: getName() 
* 
*/ 
@Test
public void testGetName() throws Exception { 
//TODO: Test goes here...
    SeatClass sc = SeatClass.FIRSTCLASS;
    //∂œ—‘”Ô∑®
    Assert.assertEquals("FirstClassSeat",sc.getName());
} 

/** 
* 
* Method: getNameByCode(int code) 
* 
*/ 
@Test
public void testGetNameByCode() throws Exception { 
//TODO: Test goes here...
    //∂œ—‘”Ô∑®
    Assert.assertEquals("FirstClassSeat",SeatClass.getNameByCode(2));
} 


} 
