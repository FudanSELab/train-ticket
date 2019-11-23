package travelto.entity; 

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Calendar;
import java.util.Date;

/** 
* SoldTicket Tester. 
* 
* @author <Authors name> 
* @since <pre>11�� 22, 2019</pre> 
* @version 1.0 
*/ 
public class SoldTicketTest { 

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
    SoldTicket st;
    st = new SoldTicket();
    Date val = Calendar.getInstance().getTime();
    st.setTravelDate(val);
    //�����﷨
    Assert.assertEquals(st.getTravelDate(),val);

} 

/** 
* 
* Method: setTravelDate(Date travelDate) 
* 
*/ 
@Test
public void testSetTravelDate() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    Date val = Calendar.getInstance().getTime();
    st.setTravelDate(val);
    //�����﷨
    Assert.assertEquals(st.getTravelDate(),val);
} 

/** 
* 
* Method: getTrainNumber() 
* 
*/ 
@Test
public void testGetTrainNumber() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    String trainNumber= "myNum";
    st.setTrainNumber(trainNumber);
    //�����﷨
    Assert.assertEquals(st.getTrainNumber(),trainNumber);

} 

/** 
* 
* Method: setTrainNumber(String trainNumber) 
* 
*/ 
@Test
public void testSetTrainNumber() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    String trainNumber= "myNum";
    st.setTrainNumber(trainNumber);
    //�����﷨
    Assert.assertEquals(st.getTrainNumber(),trainNumber);
} 

/** 
* 
* Method: getNoSeat() 
* 
*/ 
@Test
public void testGetNoSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  noseat= 0;
    st.setNoSeat(noseat);
    //�����﷨
    Assert.assertEquals(st.getNoSeat(),noseat);

} 

/** 
* 
* Method: setNoSeat(int noSeat) 
* 
*/ 
@Test
public void testSetNoSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  noseat= 0;
    st.setNoSeat(noseat);
    //�����﷨
    Assert.assertEquals(st.getNoSeat(),noseat);
} 

/** 
* 
* Method: getBusinessSeat() 
* 
*/ 
@Test
public void testGetBusinessSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setBusinessSeat(num);
    //�����﷨
    Assert.assertEquals(st.getBusinessSeat(),num);

} 

/** 
* 
* Method: setBusinessSeat(int businessSeat) 
* 
*/ 
@Test
public void testSetBusinessSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setBusinessSeat(num);
    //�����﷨
    Assert.assertEquals(st.getBusinessSeat(),num);
} 

/** 
* 
* Method: getFirstClassSeat() 
* 
*/ 
@Test
public void testGetFirstClassSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setFirstClassSeat(num);
    //�����﷨
    Assert.assertEquals(st.getFirstClassSeat(),num);
} 

/** 
* 
* Method: setFirstClassSeat(int firstClassSeat) 
* 
*/ 
@Test
public void testSetFirstClassSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setFirstClassSeat(num);
    //�����﷨
    Assert.assertEquals(st.getFirstClassSeat(),num);
} 

/** 
* 
* Method: getSecondClassSeat() 
* 
*/ 
@Test
public void testGetSecondClassSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSecondClassSeat(num);
    //�����﷨
    Assert.assertEquals(st.getSecondClassSeat(),num);
} 

/** 
* 
* Method: setSecondClassSeat(int secondClassSeat) 
* 
*/ 
@Test
public void testSetSecondClassSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSecondClassSeat(num);
    //�����﷨
    Assert.assertEquals(st.getSecondClassSeat(),num);
} 

/** 
* 
* Method: getHardSeat() 
* 
*/ 
@Test
public void testGetHardSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setHardSeat(num);
    //�����﷨
    Assert.assertEquals(st.getHardSeat(),num);
} 

/** 
* 
* Method: setHardSeat(int hardSeat) 
* 
*/ 
@Test
public void testSetHardSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setHardSeat(num);
    //�����﷨
    Assert.assertEquals(st.getHardSeat(),num);
} 

/** 
* 
* Method: getSoftSeat() 
* 
*/ 
@Test
public void testGetSoftSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSoftSeat(num);
    //�����﷨
    Assert.assertEquals(st.getSoftSeat(),num);
} 

/** 
* 
* Method: setSoftSeat(int softSeat) 
* 
*/ 
@Test
public void testSetSoftSeat() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSoftSeat(num);
    //�����﷨
    Assert.assertEquals(st.getSoftSeat(),num);
} 

/** 
* 
* Method: getHardBed() 
* 
*/ 
@Test
public void testGetHardBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setHardBed(num);
    //�����﷨
    Assert.assertEquals(st.getHardBed(),num);
} 

/** 
* 
* Method: setHardBed(int hardBed) 
* 
*/ 
@Test
public void testSetHardBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setHardBed(num);
    //�����﷨
    Assert.assertEquals(st.getHardBed(),num);
} 

/** 
* 
* Method: getSoftBed() 
* 
*/ 
@Test
public void testGetSoftBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSoftBed(num);
    //�����﷨
    Assert.assertEquals(st.getSoftBed(),num);
} 

/** 
* 
* Method: setSoftBed(int softBed) 
* 
*/ 
@Test
public void testSetSoftBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 0;
    st.setSoftBed(num);
    //�����﷨
    Assert.assertEquals(st.getSoftBed(),num);
} 

/** 
* 
* Method: getHighSoftBed() 
* 
*/ 
@Test
public void testGetHighSoftBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 1;
    st.setHighSoftBed(num);
    //�����﷨
    Assert.assertEquals(st.getHighSoftBed(),num);
} 

/** 
* 
* Method: setHighSoftBed(int highSoftBed) 
* 
*/ 
@Test
public void testSetHighSoftBed() throws Exception { 
//TODO: Test goes here...
    SoldTicket st;
    st = new SoldTicket();
    int  num= 1;
    st.setHighSoftBed(num);
    //�����﷨
    Assert.assertEquals(st.getHighSoftBed(),num);
} 


} 
