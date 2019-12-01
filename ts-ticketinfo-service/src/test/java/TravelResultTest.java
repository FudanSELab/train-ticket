import org.junit.Before;
import org.junit.Test;
import ticketinfo.entity.TrainType;
import ticketinfo.entity.TravelResult;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TravelResultTest {

    private TravelResult travelResult;

    @Before
    public void setUp() {
        travelResult = new TravelResult();
    }

    @Test
    public void testID() {
        HashMap<String, String> prices = new HashMap<String, String>();
        TrainType trainType = new TrainType("321", 7, 8);
        prices.put("Normal", "92");
        travelResult.setStatus(true);
        travelResult.setPercent(0.75);
        travelResult.setTrainType(trainType);
        travelResult.setPrices(prices);
        travelResult.setMessage("HelloWorld");
        assertThat(travelResult.isStatus(), is(true));
        assertThat(travelResult.getPercent(), is(0.75));
        assertThat(travelResult.getTrainType(), is(trainType));
        assertThat(travelResult.getPrices().get("Normal"), is("92"));
        assertThat(travelResult.getMessage(), is("HelloWorld"));
    }
}