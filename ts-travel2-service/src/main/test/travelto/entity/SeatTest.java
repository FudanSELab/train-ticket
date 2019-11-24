package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Calendar;
import java.util.Date;

/** 
* Seat Tester. 
* 
* @author <Authors name> 
* @since <pre>11‘¬ 22, 2019</pre> 
* @version 1.0 
*/ 
public class SeatTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTravelDate() 
* 
*/ 
@Test
public void testGetTravelDate() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    Date val = Calendar.getInstance().getTime();
    se.setTravelDate(val);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(se.getTravelDate(),val);
} 

/** 
* 
* Method: setTravelDate(Date travelDate) 
* 
*/ 
@Test
public void testSetTravelDate() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    Date val = Calendar.getInstance().getTime();
    se.setTravelDate(val);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(se.getTravelDate(),val);
} 

/** 
* 
* Method: getTrainNumber() 
* 
*/ 
@Test
public void testGetTrainNumber() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String trainNumber="myTrainNum";
    se.setTrainNumber(trainNumber);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(trainNumber,se.getTrainNumber());

} 

/** 
* 
* Method: setTrainNumber(String trainNumber) 
* 
*/ 
@Test
public void testSetTrainNumber() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String trainNumber="myTrainNum";
    se.setTrainNumber(trainNumber);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(trainNumber,se.getTrainNumber());
} 

/** 
* 
* Method: getStartStation() 
* 
*/ 
@Test
public void testGetStartStation() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String station="shanghai";
    se.setStartStation(station);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(station,se.getStartStation());
} 

/** 
* 
* Method: setStartStation(String startStation) 
* 
*/ 
@Test
public void testSetStartStation() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String station="shanghai";
    se.setStartStation(station);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(station,se.getStartStation());
} 

/** 
* 
* Method: getDestStation() 
* 
*/ 
@Test
public void testGetDestStation() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String station="shanghai";
    se.setDestStation(station);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(station,se.getDestStation());
} 

/** 
* 
* Method: setDestStation(String destStation) 
* 
*/ 
@Test
public void testSetDestStation() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    String station="shanghai";
    se.setDestStation(station);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(station,se.getDestStation());
} 

/** 
* 
* Method: getSeatType() 
* 
*/ 
@Test
public void testGetSeatType() throws Exception { 
//TODO: Test goes here...
    Seat se;
    se = new Seat();
    int t=1;
    se.setSeatType(t);
    //∂œ—‘”Ô∑®
    Assert.assertEquals(t,se.getSeatType());

} 

/** 
* 
* Method: setSeatType(int seatType) 
* 
*/ 
@Test
public void testSetSeatType() throws Exception { 
//TODO: Test goes here... 
} 


} 
