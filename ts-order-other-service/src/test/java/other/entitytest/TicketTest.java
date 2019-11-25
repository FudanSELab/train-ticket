package other.entitytest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import other.entity.Ticket;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {

    @Test
    void getSeatNo() {
        Ticket tickettest =  new Ticket();
        tickettest.setSeatNo(133);
        Assert.assertEquals(tickettest.getSeatNo(),133);
    }

    @Test
    void setSeatNo() {
        Ticket tickettest =  new Ticket();
        tickettest.setSeatNo(133);
        Assert.assertEquals(tickettest.getSeatNo(),133);
    }

    @Test
    void getStartStation() {
        Ticket tickettest =new Ticket();
        tickettest.setStartStation("shanghai");
        Assert.assertEquals(tickettest.getStartStation(),"shanghai");
    }

    @Test
    void setStartStation() {
        Ticket tickettest =new Ticket();
        tickettest.setStartStation("shanghai");
        Assert.assertEquals(tickettest.getStartStation(),"shanghai");
    }


    @Test
    void getDestStation() {
        Ticket tickettest =new Ticket();
        tickettest.setDestStation("shanghai");
        Assert.assertEquals(tickettest.getDestStation(),"shanghai");
    }

    @Test
    void setDestStation() {
        Ticket tickettest =new Ticket();
        tickettest.setDestStation("shanghai");
        Assert.assertEquals(tickettest.getDestStation(),"shanghai");
    }
}