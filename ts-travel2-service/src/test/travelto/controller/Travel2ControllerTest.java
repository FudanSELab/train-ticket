package travelto.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import travelto.entity.TravelInfo;
import travelto.entity.TripInfo;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Travel2Controller Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 24, 2019</pre>
 */
public class Travel2ControllerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: home(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testHome() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        controller.home(null);
    }

    /**
     * Method: getTrainTypeByTripId(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetTrainTypeByTripId() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.getTrainTypeByTripId("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: getRouteByTripId(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetRouteByTripId() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.getRouteByTripId("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: getTripsByRouteId(@RequestBody List<String> routeIds, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetTripsByRouteId() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.getTripsByRouteId(new ArrayList<>(), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: createTrip(@RequestBody TravelInfo routeIds, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testCreateTrip() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.createTrip(new TravelInfo(), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: retrieve(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testRetrieve() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.retrieve("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * Method: updateTrip(@RequestBody TravelInfo info, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testUpdateTrip() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.updateTrip(new TravelInfo(), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: deleteTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testDeleteTrip() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.deleteTrip("1", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: queryInfo(@RequestBody TripInfo info, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testQueryInfo() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            TripInfo val = new TripInfo();
            controller.queryInfo(val, null);
            val.setStartingPlace("start");
            controller.queryInfo(val, null);
            val.setEndPlace("end");
            controller.queryInfo(val, null);
            val.setDepartureTime(Calendar.getInstance().getTime());
            controller.queryInfo(val, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * Method: queryAll(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testQueryAll() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.queryAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: adminQueryAll(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testAdminQueryAll() throws Exception {
        Travel2Controller controller = new Travel2Controller();
        try {
            controller.adminQueryAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


} 
