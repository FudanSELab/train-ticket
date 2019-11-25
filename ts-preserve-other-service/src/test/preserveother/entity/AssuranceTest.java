package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Assurance;
import preserveother.entity.AssuranceType;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class AssuranceTest {

    private UUID testId;
    private UUID testOrderId;
    private AssuranceType testAssuranceType;
    private Assurance assurance;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        testOrderId = UUID.randomUUID();
        testAssuranceType = AssuranceType.TRAFFIC_ACCIDENT;
        assurance = new Assurance(testId, testOrderId, testAssuranceType);
    }

    @Test
    public void getId() {
        assertEquals(assurance.getId(), testId);
    }

    @Test
    public void getOrderId() {
        assertEquals(assurance.getOrderId(), testOrderId);
    }

    @Test
    public void getType() {
        assertEquals(assurance.getType(), testAssuranceType);
    }

    @Test
    public void setId() {
        UUID newId = UUID.randomUUID();
        assurance.setId(newId);
        assertEquals(assurance.getId(), newId);
    }

    @Test
    public void setOrderId() {
        UUID newOrderId = UUID.randomUUID();
        assurance.setOrderId(newOrderId);
        assertEquals(assurance.getOrderId(), newOrderId);
    }

    @Test
    public void setType() {
        assurance.setType(null);
        assertNull(assurance.getType());
    }
}