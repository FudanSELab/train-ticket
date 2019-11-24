package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Gender;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class GenderTest {
    private Gender none;
    private Gender male;
    private Gender female;
    private Gender other;

    @Before
    public void setUp() {
        none = Gender.NONE;
        male = Gender.MALE;
        female = Gender.FEMALE;
        other = Gender.OTHER;
    }

    @Test
    public void getCode() {
        assertEquals(none.getCode(), 0);
        assertEquals(male.getCode(), 1);
        assertEquals(female.getCode(), 2);
        assertEquals(other.getCode(), 3);
    }

    @Test
    public void getName() {
        assertEquals(none.getName(), "Null");
        assertEquals(male.getName(), "Male");
        assertEquals(female.getName(), "Female");
        assertEquals(other.getName(), "Other");
    }

    @Test
    public void getNameByCode() {
        assertEquals(Gender.getNameByCode(0), none.getName());
        assertEquals(Gender.getNameByCode(1), male.getName());
        assertEquals(Gender.getNameByCode(2), female.getName());
        assertEquals(Gender.getNameByCode(3), other.getName());
        assertEquals(Gender.getNameByCode(4), none.getName());
    }
}