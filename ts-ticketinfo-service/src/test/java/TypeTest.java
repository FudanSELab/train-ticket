import org.junit.Test;
import ticketinfo.entity.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TypeTest {

    @Test
    public void testID() {
        for (Type T : Type.values()) {
            assertNotEquals(null, Type.getName(T.getIndex()));
            assertNotEquals("", T.getName());
        }
        assertEquals( null, Type.getName(-1));
    }
}