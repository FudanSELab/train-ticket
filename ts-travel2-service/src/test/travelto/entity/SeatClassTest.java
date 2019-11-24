package travelto.entity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * SeatClass Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11�� 22, 2019</pre>
 */
public class SeatClassTest {

    @Before
    public void before() throws Exception {
        Assert.assertEquals(SeatClass.getNameByCode(100), "NoSeat");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getCode()
     */
    @Test
    public void testGetCode() throws Exception {
//TODO: Test goes here...
        SeatClass sc = SeatClass.FIRSTCLASS;
        //�����﷨
        Assert.assertEquals(2, sc.getCode());

    }

    /**
     * Method: getName()
     */
    @Test
    public void testGetName() throws Exception {
//TODO: Test goes here...
        SeatClass sc = SeatClass.FIRSTCLASS;
        //�����﷨
        Assert.assertEquals("FirstClassSeat", sc.getName());
    }

    /**
     * Method: getNameByCode(int code)
     */
    @Test
    public void testGetNameByCode() throws Exception {
//TODO: Test goes here...
        //�����﷨
        Assert.assertEquals("FirstClassSeat", SeatClass.getNameByCode(2));
    }


} 
