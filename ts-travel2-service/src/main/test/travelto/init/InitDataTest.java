package travelto.init;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Date;

import static travelto.init.InitData.BJ;
import static travelto.init.InitData.SH;

/**
 * InitData Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11ÔÂ 24, 2019</pre>
 */
public class InitDataTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: buildTravelInfo(String... args)
     */
    @Test
    public void testBuildTravelInfo() throws Exception {
        InitData initData = new InitData();
        initData.buildTravelInfo("Z1234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90b04",
                SH, SH, BJ, "Mon 5 04 09:51:52 GMT+0800 2013", "Mon 5 04 15:51:52 GMT+0800 2013", null);
    }

    /**
     * Method: run(String... args)
     */
    @Test
    public void testRun() throws Exception {
        InitData initData = new InitData();
        String[] args = new String[]{""};
        try {
            initData.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * Method: str2Date(String s)
     */
    @Test
    public void testStr2Date() throws Exception {
        String str = "Mon 5 04 15:51:52 GMT+0800 2013";
        Date date1 = InitData.str2Date(str);
        String str1 = "Mon 5 04 15:51 GMT+08 2013";
        Date date2 = InitData.str2Date(str1);
    }

} 
