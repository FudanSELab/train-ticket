package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Seat;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class SeatTest {

    private Date travelDate;
    private String trainNumber;
    private String startStation;
    private String destStation;
    private int seatType;
    private Seat seat;

    @Before
    public void setUp() {
        travelDate = new Date(111111);
        trainNumber = "K1048";
        startStation = "shanghai";
        destStation = "beijing";
        seatType = 1;
        seat = new Seat(travelDate, trainNumber, startStation, destStation, seatType);
    }

    @Test
    public void getTravelDate() {
        assertEquals(seat.getTravelDate(), travelDate);
    }

    @Test
    public void setTravelDate() {
        Date newTravelDate = new Date(222222);
        seat.setTravelDate(newTravelDate);
        assertEquals(seat.getTravelDate(), newTravelDate);
    }

    @Test
    public void getTrainNumber() {
        assertEquals(seat.getTrainNumber(), trainNumber);
    }

    @Test
    public void setTrainNumber() {
        String newTrainNumber = "K1047";
        seat.setTrainNumber(newTrainNumber);
        assertEquals(seat.getTrainNumber(), newTrainNumber);
    }

    @Test
    public void getStartStation() {
        assertEquals(seat.getStartStation(), startStation);
    }

    @Test
    public void setStartStation() {
        String newStartStation = "hunan";
        seat.setStartStation(newStartStation);
        assertEquals(seat.getStartStation(), newStartStation);
    }

    @Test
    public void getDestStation() {
        assertEquals(seat.getDestStation(), destStation);
    }

    @Test
    public void setDestStation() {
        String newDesStation = "hubei";
        seat.setDestStation(newDesStation);
        assertEquals(seat.getDestStation(), newDesStation);
    }

    @Test
    public void getSeatType() {
        assertEquals(seat.getSeatType(), seatType);
    }

    @Test
    public void setSeatType() {
        int newSeatType = 2;
        seat.setSeatType(newSeatType);
        assertEquals(seat.getSeatType(), newSeatType);
    }

    @Test
    public void testDefault() {
        Seat seat = new Seat();
        assertNull(seat.getStartStation());
    }
}