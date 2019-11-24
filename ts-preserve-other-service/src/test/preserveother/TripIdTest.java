package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.TripId;
import preserveother.entity.Type;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TripIdTest {
    private TripId tripIdG;
    private TripId tripIdD;
    private TripId tripIdZ;
    private TripId tripIdT;
    private TripId tripIdK;
    private TripId tripIdNone;

    @Before
    public void setUp() {
        tripIdG = new TripId("G100");
        tripIdD = new TripId("D101");
        tripIdZ = new TripId("Z102");
        tripIdT = new TripId("T103");
        tripIdK = new TripId("K104");
        tripIdNone = new TripId("A");
    }

    @Test
    public void getType() {
        assertEquals(tripIdG.getType(), Type.G);
        assertEquals(tripIdD.getType(), Type.D);
        assertEquals(tripIdZ.getType(), Type.Z);
        assertEquals(tripIdT.getType(), Type.T);
        assertEquals(tripIdK.getType(), Type.K);
        assertNull(tripIdNone.getType());
    }

    @Test
    public void setType() {
        tripIdG.setType(Type.D);
        assertEquals(tripIdG.getType(), Type.D);
    }

    @Test
    public void getNumber() {
        assertEquals(tripIdG.getNumber(), "100");
        assertEquals(tripIdD.getNumber(), "101");
        assertEquals(tripIdZ.getNumber(), "102");
        assertEquals(tripIdT.getNumber(), "103");
        assertEquals(tripIdK.getNumber(), "104");
        assertEquals(tripIdNone.getNumber(), "");
    }

    @Test
    public void setNumber() {
        tripIdG.setNumber("1000");
        assertEquals(tripIdG.getNumber(), "1000");
    }

    @Test
    public void testToString() {
        assertEquals(tripIdD.toString(), "D101");
    }

    @Test
    public void testDefault() {
        TripId tripId = new TripId();
        assertNull(tripId.getNumber());
    }
}