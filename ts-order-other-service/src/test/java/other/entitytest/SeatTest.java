package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.Seat;

import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class SeatTest {

    @Test
    void getTravelDate() {
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date set = cld.getTime();
        Seat seat = new Seat(set,"test1","shenzhen","shanghai",1);


        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",seat.getTravelDate().toString());


    }

    @Test
    void setTravelDate() {
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date set = cld.getTime();
        Seat seat = new Seat(set,"test1","shenzhen","shanghai",1);

        seat.setTravelDate(set);
        Assert.assertEquals("Tue Dec 02 01:01:01 CST 2014",seat.getTravelDate().toString());

    }

    @Test
    void getTrainNumber() {

        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        String trainNumber = setas.getTrainNumber();
        Assert.assertEquals(trainNumber,"test1");
    }

    @Test
    void setTrainNumber() {

        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        setas.setTrainNumber("test2");
        String trainNumber = setas.getTrainNumber();
        Assert.assertEquals(trainNumber,"test2");
    }

    @Test
    void getStartStation() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);

        String start = setas.getStartStation();
        Assert.assertEquals(start,"shenzhen");
    }

    @Test
    void setStartStation() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        setas.setStartStation("hongkong");
        String start = setas.getStartStation();
        Assert.assertEquals(start,"hongkong");

    }

    @Test
    void getDestStation() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        String dst = setas.getDestStation();
        Assert.assertEquals(dst,"shanghai");
    }

    @Test
    void setDestStation() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        setas.setDestStation("san igo");
        String dst = setas.getDestStation();
        Assert.assertEquals(dst,"san igo");
    }

    @Test
    void getSeatType() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        int seatype = setas.getSeatType() ;
        Assert.assertEquals(seatype,1);
    }

    @Test
    void setSeatType() {
        Calendar cld = Calendar.getInstance();
        cld.set(2019,10,20,18,38,11);
        Date set = cld.getTime();
        Seat setas = new Seat(set,"test1","shenzhen","shanghai",1);
        setas.setSeatType(2);
        int seatype = setas.getSeatType() ;
        Assert.assertEquals(seatype,2);
    }
}