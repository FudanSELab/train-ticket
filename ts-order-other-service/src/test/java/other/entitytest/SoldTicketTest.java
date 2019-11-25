package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.SoldTicket;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SoldTicketTest {

    @Test
    void getTravelDate() {
        SoldTicket slt = new SoldTicket();


        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        slt.setTravelDate(starttime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",slt.getTravelDate().toString());
    }

    @Test
    void setTravelDate() {
        SoldTicket slt = new SoldTicket();


        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date starttime = cld.getTime();
        slt.setTravelDate(starttime);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",slt.getTravelDate().toString());

    }

    @Test
    void getTrainNumber() {
        SoldTicket slt = new SoldTicket();
        slt.setTrainNumber("test1");
        Assert.assertEquals(slt.getTrainNumber(),"test1");
    }

    @Test
    void setTrainNumber() {
        SoldTicket slt = new SoldTicket();
        slt.setTrainNumber("test1");
        Assert.assertEquals(slt.getTrainNumber(),"test1");
    }

    @Test
    void getNoSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setNoSeat(0);
        Assert.assertEquals(0,slt.getNoSeat());
    }

    @Test
    void setNoSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setNoSeat(0);
        Assert.assertEquals(0,slt.getNoSeat());
    }

    @Test
    void getBusinessSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setBusinessSeat(0);
        Assert.assertEquals(0,slt.getBusinessSeat());
    }

    @Test
    void setBusinessSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setBusinessSeat(0);
        Assert.assertEquals(0,slt.getBusinessSeat());
    }

    @Test
    void getFirstClassSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setBusinessSeat(0);
        Assert.assertEquals(0,slt.getFirstClassSeat());
    }


    @Test
    void setFirstClassSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setFirstClassSeat(1);
        Assert.assertEquals(1,slt.getFirstClassSeat());

    }

    @Test
    void getSecondClassSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setSecondClassSeat(1);
        Assert.assertEquals(1,slt.getSecondClassSeat());
    }

    @Test
    void setSecondClassSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setSecondClassSeat(1);
        Assert.assertEquals(1,slt.getSecondClassSeat());
    }

    @Test
    void getHardSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setHardSeat(1);
        Assert.assertEquals(1,slt.getHardSeat());
    }

    @Test
    void setHardSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setHardSeat(1);
        Assert.assertEquals(1,slt.getHardSeat());
    }

    @Test
    void getSoftSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setSoftSeat(1);
        Assert.assertEquals(1,slt.getSoftSeat());
    }

    @Test
    void setSoftSeat() {
        SoldTicket slt = new SoldTicket();
        slt.setSoftSeat(1);
        Assert.assertEquals(1,slt.getSoftSeat());
    }

    @Test
    void getHardBed() {
        SoldTicket slt = new SoldTicket();
        slt.setHardBed(1);
        Assert.assertEquals(1,slt.getHardBed());
    }

    @Test
    void setHardBed() {
        SoldTicket slt = new SoldTicket();
        slt.setHardBed(1);
        Assert.assertEquals(1,slt.getHardBed());
    }

    @Test
    void getSoftBed() {
        SoldTicket slt = new SoldTicket();
        slt.setSoftBed(1);
        Assert.assertEquals(1,slt.getSoftBed());
    }

    @Test
    void setSoftBed() {
        SoldTicket slt = new SoldTicket();
        slt.setSoftBed(1);
        Assert.assertEquals(1,slt.getSoftBed());
    }

    @Test
    void getHighSoftBed() {
        SoldTicket slt = new SoldTicket();
        slt.setHighSoftBed(1);
        Assert.assertEquals(1,slt.getHighSoftBed());
    }

    @Test
    void setHighSoftBed() {
        SoldTicket slt = new SoldTicket();
        slt.setHighSoftBed(1);
        Assert.assertEquals(1,slt.getHighSoftBed());
    }
}