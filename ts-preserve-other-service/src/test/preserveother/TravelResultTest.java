package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.TrainType;
import preserveother.entity.TravelResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TravelResultTest {
    private boolean status;
    private double percent;
    private TrainType trainType;
    private Map<String, String> prices;
    private TravelResult travelResult;

    @Before
    public void setUp() {
        status = true;
        percent = 0.66;
        trainType = new TrainType();
        prices = new HashMap<>();
        prices.put("0", "0.0");
        travelResult = new TravelResult(status, percent, trainType, prices);
    }

    @Test
    public void isStatus() {
        assertEquals(travelResult.isStatus(), status);
    }

    @Test
    public void setStatus() {
        boolean newStatus = false;
        travelResult.setStatus(newStatus);
        assertEquals(travelResult.isStatus(), newStatus);
    }

    @Test
    public void getPercent() {
        assertEquals(travelResult.getPercent(), percent, 0.1);
    }

    @Test
    public void setPercent() {
        double newPercent = 0.99;
        travelResult.setPercent(newPercent);
        assertEquals(travelResult.getPercent(), newPercent, 0.1);
    }

    @Test
    public void getTrainType() {
        assertEquals(travelResult.getTrainType(), trainType);
    }

    @Test
    public void setTrainType() {
        TrainType newTrainType = new TrainType();
        travelResult.setTrainType(newTrainType);
        assertEquals(travelResult.getTrainType(), newTrainType);
    }

    @Test
    public void getPrices() {
        assertEquals(travelResult.getPrices(), prices);
    }

    @Test
    public void setPrices() {
        Map<String, String> newPrices = new HashMap<>();
        newPrices.put("1", "1.0");
        travelResult.setPrices(newPrices);
        assertEquals(travelResult.getPrices(), newPrices);
    }

    @Test
    public void testDefault() {
        TravelResult defaultTravelResult = new TravelResult();
        assertNull(defaultTravelResult.getPrices());
    }
}