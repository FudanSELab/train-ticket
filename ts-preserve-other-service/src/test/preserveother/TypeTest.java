package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.Type;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class TypeTest {
    private Type g;
    private Type d;
    private Type z;
    private Type t;
    private Type k;

    @Before
    public void setUp() {
        g = Type.G;
        d = Type.D;
        z = Type.Z;
        t = Type.T;
        k = Type.K;
    }

    @Test
    public void getName() {
        assertEquals(g.getName(), "G");
        assertEquals(d.getName(), "D");
        assertEquals(z.getName(), "Z");
        assertEquals(t.getName(), "T");
        assertEquals(k.getName(), "K");
    }

    @Test
    public void testGetName() {
        assertEquals(Type.getName(1), "G");
        assertEquals(Type.getName(2), "D");
        assertEquals(Type.getName(3), "Z");
        assertEquals(Type.getName(4), "T");
        assertEquals(Type.getName(5), "K");
        assertNull(Type.getName(6));
    }

    @Test
    public void getIndex() {
        assertEquals(g.getIndex(), 1);
        assertEquals(d.getIndex(), 2);
        assertEquals(z.getIndex(), 3);
        assertEquals(t.getIndex(), 4);
        assertEquals(k.getIndex(), 5);
    }
}