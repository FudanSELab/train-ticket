package other.entitytest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import other.entity.LeftTicketInfo;
import other.entity.Ticket;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class LeftTicketInfoTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSoldTickets() {
        LeftTicketInfo test1 = new LeftTicketInfo();
        Ticket ticket11 = new Ticket();
        Ticket ticket22 = new Ticket();
        ticket11.setSeatNo(123);
        ticket11.setDestStation("guangzhou");
        ticket11.setStartStation("shenzhen");

        ticket22.setSeatNo(456);
        ticket22.setDestStation("shanghai");
        ticket22.setStartStation("shenzhen");
        Set<Ticket> ticklist3 = new HashSet<>();
        ticklist3.add(ticket11);
        ticklist3.add(ticket22);
        test1.setSoldTickets(ticklist3);

        Set<Ticket> getlist = test1.getSoldTickets();
        Iterator<Ticket> gettest3 = getlist.iterator();
        int no1 = gettest3.next().getSeatNo();
        int no2 = gettest3.next().getSeatNo();

        Assert.assertEquals(no1,123);
        Assert.assertEquals(no2,456);

    }

    @Test
    void setSoldTickets() {
        LeftTicketInfo test1 = new LeftTicketInfo();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        ticket1.setSeatNo(123);
        ticket1.setDestStation("guangzhou");
        ticket1.setStartStation("shenzhen");

        ticket2.setSeatNo(456);
        ticket2.setDestStation("shanghai");
        ticket2.setStartStation("shenzhen");
        Set<Ticket> ticklist1 = new HashSet<>();
        ticklist1.add(ticket1);
        ticklist1.add(ticket2);
        test1.setSoldTickets(ticklist1);

        Set<Ticket> getlist = test1.getSoldTickets();
        Iterator<Ticket> gettest2 = getlist.iterator();
        int no3 = gettest2.next().getSeatNo();
        int no4 = gettest2.next().getSeatNo();
        Assert.assertEquals(no3,123);
        Assert.assertEquals(no4,456);
    }

//    @Test
//    void testToString() {
//        LeftTicketInfo test1 = new LeftTicketInfo();
//        Ticket ticket1 = new Ticket();
//        Ticket ticket2 = new Ticket();
//        ticket1.setSeatNo(123);
//        ticket1.setDestStation("guangzhou");
//        ticket1.setStartStation("shenzhen");
//
//        ticket2.setSeatNo(456);
//        ticket2.setDestStation("shanghai");
//        ticket2.setStartStation("shenzhen");
//        Set<Ticket> ticklist = new HashSet<>();
//        ticklist.add(ticket1);
//        ticklist.add(ticket2);
//        test1.setSoldTickets(ticklist);
//        System.out.println();
//        Assert.assertEquals("LeftTicketInfo{soldTickets=[other.entity.Ticket@b2c9a9c, other.entity.Ticket@4c178a76]}",test1.getSoldTickets().toString());
//        Assert.assertEquals();

    }
