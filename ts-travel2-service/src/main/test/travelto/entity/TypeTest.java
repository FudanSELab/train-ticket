package travelto.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Type Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Ê®Ò»ÔÂ 22, 2019</pre>
 */
public class TypeTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getName(int index)
     */
    @Test
    public void testGetNameIndex() throws Exception {
//TODO: Test goes here...
        Assert.assertEquals(Type.getName(3), "Z");
        Assert.assertEquals(Type.getName(0), null);
    }

    /**
     * Method: getName()
     */
    @Test
    public void testGetName() throws Exception {
//TODO: Test goes here...
        Type val = Type.Z;
        Assert.assertEquals(val.getName(), "Z");
    }

    /**
     * Method: getIndex()
     */
    @Test
    public void testGetIndex() throws Exception {
//TODO: Test goes here...
        Type val = Type.Z;
        Assert.assertEquals(val.getIndex(), 3);
    }


} 
