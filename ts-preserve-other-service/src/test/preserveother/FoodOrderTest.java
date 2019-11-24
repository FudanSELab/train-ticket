package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.FoodOrder;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class FoodOrderTest {
    private UUID testId;
    private UUID testOrderId;
    private int testFoodType;
    private String testStationName;
    private String testStoreName;
    private String testFoodName;
    private double testPrice;
    private FoodOrder testFoodOrder;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        testFoodType = 1;
        testStationName = "Shanghai";
        testStoreName = "familymart";
        testFoodName = "bread";
        testPrice = 6.6;
        testFoodOrder = new FoodOrder(testId, testOrderId, testFoodType, testStationName, testStoreName, testFoodName, testPrice);

    }

    @Test
    public void getId() {
        assertEquals(testFoodOrder.getId(), testId);
    }

    @Test
    public void setId() {
        UUID newId = UUID.randomUUID();
        testFoodOrder.setId(newId);
        assertEquals(testFoodOrder.getId(), newId);
    }

    @Test
    public void getOrderId() {
        assertEquals(testFoodOrder.getOrderId(), testOrderId);
    }

    @Test
    public void setOrderId() {
        UUID newOrderId = UUID.randomUUID();
        testFoodOrder.setOrderId(newOrderId);
        assertEquals(testFoodOrder.getOrderId(), newOrderId);
    }

    @Test
    public void getFoodType() {
        assertEquals(testFoodOrder.getFoodType(), testFoodType);
    }

    @Test
    public void setFoodType() {
        int newFoodType = 2;
        testFoodOrder.setFoodType(newFoodType);
        assertEquals(testFoodOrder.getFoodType(), newFoodType);
    }

    @Test
    public void getStationName() {
        assertEquals(testFoodOrder.getStationName(), testStationName);
    }

    @Test
    public void setStationName() {
        String newStationName = "beijing";
        testFoodOrder.setStationName(newStationName);
        assertEquals(testFoodOrder.getStationName(), newStationName);
    }

    @Test
    public void getStoreName() {
        assertEquals(testFoodOrder.getStoreName(), testStoreName);
    }

    @Test
    public void setStoreName() {
        String newStoreName = "lawson";
        testFoodOrder.setStoreName(newStoreName);
        assertEquals(testFoodOrder.getStoreName(), newStoreName);
    }

    @Test
    public void getFoodName() {
        assertEquals(testFoodOrder.getFoodName(), testFoodName);
    }

    @Test
    public void setFoodName() {
        String newFoodName = "pizza";
        testFoodOrder.setFoodName(newFoodName);
        assertEquals(testFoodOrder.getFoodName(), newFoodName);
    }

    @Test
    public void getPrice() {
        assertEquals(testFoodOrder.getPrice(), testPrice, 0.1);
    }

    @Test
    public void setPrice() {
        double newPrice = 9.9;
        testFoodOrder.setPrice(newPrice);
        assertEquals(testFoodOrder.getPrice(), newPrice, 0.1);
    }

    @Test
    public void testDefault() {
        assertNotNull(new FoodOrder());
    }
}