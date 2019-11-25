package travelto.init;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import travelto.entity.Trip;
import travelto.entity.TripId;
import travelto.repository.TripRepository;
import travelto.service.TravelToServiceImpl;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelto.init.InitData.BJ;
import static travelto.init.InitData.SH;

/**
 * InitData Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11�� 24, 2019</pre>
 */
public class InitDataTest {

    TripRepository tripRepository;
    InitData initData;
    RestTemplate restTemplate;

    @Before
    public void before() throws Exception {
        initData = new InitData();
        initData.service = new TravelToServiceImpl();
        tripRepository = mock(TripRepository.class);
        restTemplate = mock(RestTemplate.class);
        ((TravelToServiceImpl) initData.service).testInit(tripRepository, restTemplate);

        ArrayList<Trip> data = new ArrayList<>();
        Trip trip = new Trip();
        trip.setTripId(new TripId("Z88"));
        data.add(trip);
        when(tripRepository.findByRouteId("1")).thenReturn(data);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: buildTravelInfo(String... args)
     */
    @Test
    public void testBuildTravelInfo() throws Exception {
        initData.buildTravelInfo("Z1234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90b04",
                SH, SH, BJ, "Mon 5 04 09:51:52 GMT+0800 2013", "Mon 5 04 15:51:52 GMT+0800 2013", null);
    }

    /**
     * Method: run(String... args)
     */
    @Test
    public void testRun() throws Exception {
        try {
            initData.run();
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
