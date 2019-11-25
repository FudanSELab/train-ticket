package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.AssuranceType;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)

public class AssuranceTypeTest {

    private AssuranceType type;

    @Before
    public void setUp() {
        type = AssuranceType.TRAFFIC_ACCIDENT;
    }

    @Test
    public void getIndex() {
        assertEquals(type.getIndex(), 1);
    }

    @Test
    public void getName() {
        assertEquals(type.getName(), "Traffic Accident Assurance");
    }

    @Test
    public void getPrice() {
        assertEquals(type.getPrice(), 3.0, 0.01);
    }

    @Test
    public void getTypeByIndex() {
        assertEquals(AssuranceType.getTypeByIndex(1), type);
        assertNull(AssuranceType.getTypeByIndex(2));
    }
}