import org.junit.Before;
import org.junit.Test;
import ticketinfo.entity.TrainType;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrainTypeTest {

    private TrainType trainType1, trainType2;

    @Before
    public void setUp() {
        trainType1 = new TrainType();
        trainType2 = new TrainType("321", 7, 8);
    }

    @Test
    public void testID() {
        assertThat(trainType2.getId(), is("321"));
        assertThat(trainType2.getEconomyClass(), is(7));
        assertThat(trainType2.getConfortClass(), is(8));
        trainType1.setId("456");
        trainType1.setEconomyClass(4);
        trainType1.setConfortClass(5);
        trainType1.setAverageSpeed(6);
        assertThat(trainType1.getId(), is("456"));
        assertThat(trainType1.getEconomyClass(), is(4));
        assertThat(trainType1.getConfortClass(), is(5));
        assertThat(trainType1.getAverageSpeed(), is(6));
    }
}