package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.TrainType;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TrainTypeTest {
    private String id;
    private int economyClass;
    private int confortClass;
    private TrainType trainType;

    @Before
    public void setUp() {
        id = "1";
        economyClass = 1;
        confortClass = 1;
        trainType = new TrainType(id, economyClass, confortClass);
    }

    @Test
    public void getId() {
        assertEquals(trainType.getId(), id);
    }

    @Test
    public void setId() {
        String newId = "2";
        trainType.setId(newId);
        assertEquals(trainType.getId(), newId);
    }

    @Test
    public void getEconomyClass() {
        assertEquals(trainType.getEconomyClass(), economyClass);
    }

    @Test
    public void setEconomyClass() {
        int newEconomyClass = 2;
        trainType.setEconomyClass(newEconomyClass);
        assertEquals(trainType.getEconomyClass(), newEconomyClass);
    }

    @Test
    public void getConfortClass() {
        assertEquals(trainType.getConfortClass(), confortClass);
    }

    @Test
    public void setConfortClass() {
        int newConfortClass = 2;
        trainType.setConfortClass(newConfortClass);
        assertEquals(trainType.getConfortClass(), newConfortClass);
    }

    @Test
    public void testDefault() {
        TrainType defaultTrainType = new TrainType();
        assertNull(defaultTrainType.getId());
    }
}