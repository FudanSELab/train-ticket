package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Ticket;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TicketTest {
    private int seatNo;
    private String startStation;
    private String destStation;
    private Ticket ticket;

    @Before
    public void setUp() {
        seatNo = 1;
        startStation = "shanghai";
        destStation = "beijing";
        ticket = new Ticket(seatNo, startStation, destStation);
    }

    @Test
    public void getSeatNo() {
        assertEquals(ticket.getSeatNo(), seatNo);
    }

    @Test
    public void setSeatNo() {
        int newSeatNo = 2;
        ticket.setSeatNo(newSeatNo);
        assertEquals(ticket.getSeatNo(), newSeatNo);
    }

    @Test
    public void getStartStation() {
        assertEquals(ticket.getStartStation(), startStation);
    }

    @Test
    public void setStartStation() {
        String newStartStation = "hunan";
        ticket.setStartStation(newStartStation);
        assertEquals(ticket.getStartStation(), newStartStation);
    }

    @Test
    public void getDestStation() {
        assertEquals(ticket.getDestStation(), destStation);
    }

    @Test
    public void setDestStation() {
        String newDesStation = "hubei";
        ticket.setDestStation(newDesStation);
        assertEquals(ticket.getDestStation(), newDesStation);
    }

    @Test
    public void testDefault() {
        Ticket defaultTicket = new Ticket();
        assertNull(defaultTicket.getStartStation());
    }
}