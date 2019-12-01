import org.junit.Before;
import org.junit.Test;
import ticketinfo.entity.TripId;
import ticketinfo.entity.Type;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TripIdTest {

    private TripId tripIdA;

    @Before
    public void setUp() {
        tripIdA = new TripId("G7512");
    }

    @Test
    public void testID() {
        assertThat(tripIdA.getType(), is(Type.G));
        assertThat(tripIdA.getNumber(), is("7512"));
        tripIdA = new TripId("C7512");
        tripIdA = new TripId("Z7512");
        tripIdA = new TripId("T7512");
        tripIdA = new TripId("K7512");
        tripIdA.setType(Type.D);
        tripIdA.setNumber("5048");
        assertThat(tripIdA.toString(), is("D5048"));
    }
}