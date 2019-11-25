package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.SeatClass;

import static org.junit.jupiter.api.Assertions.*;

class SeatClassTest {

    @Test
    void getNameByCode() {
        String seClass0 = SeatClass.getNameByCode(0);
        String seClass1 = SeatClass.getNameByCode(1);
        String seClass2 = SeatClass.getNameByCode(2);
        String seClass3 = SeatClass.getNameByCode(3);
        String seClass4 = SeatClass.getNameByCode(4);
        String seClass5 = SeatClass.getNameByCode(5);
        String seClass6 = SeatClass.getNameByCode(6);
        String seClass7 = SeatClass.getNameByCode(7);
        String seClass8 = SeatClass.getNameByCode(8);

        Assert.assertEquals(seClass0,"NoSeat");
        Assert.assertEquals(seClass1,"GreenSeat");
        Assert.assertEquals(seClass2,"FirstClassSeat");
        Assert.assertEquals(seClass3,"SecondClassSeat");
        Assert.assertEquals(seClass4,"HardSeat");
        Assert.assertEquals(seClass5,"SoftSeat");
        Assert.assertEquals(seClass6,"HardBed");
        Assert.assertEquals(seClass7,"SoftBed");
        Assert.assertEquals(seClass8,"HighSoftSeat");


    }

}