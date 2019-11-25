package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.SeatClass;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class SeatClassTest {
    private SeatClass none;
    private SeatClass business;
    private SeatClass firstClass;
    private SeatClass secondClass;
    private SeatClass hardSeat;
    private SeatClass softSeat;
    private SeatClass hardBed;
    private SeatClass softBed;
    private SeatClass highSoftBed;

    @Before
    public void setUp() {
        none = SeatClass.NONE;
        business = SeatClass.BUSINESS;
        firstClass = SeatClass.FIRSTCLASS;
        secondClass = SeatClass.SECONDCLASS;
        hardSeat = SeatClass.HARDSEAT;
        softSeat = SeatClass.SOFTSEAT;
        hardBed = SeatClass.HARDBED;
        softBed = SeatClass.SOFTBED;
        highSoftBed = SeatClass.HIGHSOFTBED;
    }

    @Test
    public void getCode() {
        assertEquals(none.getCode(), 0);
        assertEquals(business.getCode(), 1);
        assertEquals(firstClass.getCode(), 2);
        assertEquals(secondClass.getCode(), 3);
        assertEquals(hardSeat.getCode(), 4);
        assertEquals(softSeat.getCode(), 5);
        assertEquals(hardBed.getCode(), 6);
        assertEquals(softBed.getCode(), 7);
        assertEquals(highSoftBed.getCode(), 8);
    }

    @Test
    public void getName() {
        assertEquals(none.getName(), "NoSeat");
        assertEquals(business.getName(), "GreenSeat");
        assertEquals(firstClass.getName(), "FirstClassSeat");
        assertEquals(secondClass.getName(), "SecondClassSeat");
        assertEquals(hardSeat.getName(), "HardSeat");
        assertEquals(softSeat.getName(), "SoftSeat");
        assertEquals(hardBed.getName(), "HardBed");
        assertEquals(softBed.getName(), "SoftBed");
        assertEquals(highSoftBed.getName(), "HighSoftSeat");
    }

    @Test
    public void getNameByCode() {
        assertEquals(SeatClass.getNameByCode(0), "NoSeat");
        assertEquals(SeatClass.getNameByCode(1), "GreenSeat");
        assertEquals(SeatClass.getNameByCode(2), "FirstClassSeat");
        assertEquals(SeatClass.getNameByCode(3), "SecondClassSeat");
        assertEquals(SeatClass.getNameByCode(4), "HardSeat");
        assertEquals(SeatClass.getNameByCode(5), "SoftSeat");
        assertEquals(SeatClass.getNameByCode(6), "HardBed");
        assertEquals(SeatClass.getNameByCode(7), "SoftBed");
        assertEquals(SeatClass.getNameByCode(8), "HighSoftSeat");
        assertEquals(SeatClass.getNameByCode(9), "NoSeat");
    }
}