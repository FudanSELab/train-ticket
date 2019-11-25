package travelto.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import travelto.entity.*;
import travelto.repository.TripRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TravelToServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11ÔÂ 22, 2019</pre>
 */
public class TravelToServiceImplTest {

    TripRepository tripRepository;
    RestTemplate restTemplate;
    TravelToServiceImpl travelToService;



    @Before
    public void before() throws Exception {
        testPlaceInfoWrapper();

        travelToService = new TravelToServiceImpl();
        tripRepository = mock(TripRepository.class);
        restTemplate = mock(RestTemplate.class);
        travelToService.testInit(tripRepository, restTemplate);

        when(tripRepository.findByTripId(new TripId())).thenReturn(null);
        when(tripRepository.findAll()).thenReturn(null);



    }

    @After
    public void after() throws Exception {
    }

    private void testPlaceInfoWrapper() {
        TravelToServiceImpl.PlaceInfoWrapper val = new TravelToServiceImpl.PlaceInfoWrapper(
                new Trip(), new Route(), "1", "2", "s", "e");
        TravelToServiceImpl.PlaceInfoWrapper val2 = new TravelToServiceImpl.PlaceInfoWrapper(
                new Trip(), new Route(), "1", "2");
        TravelToServiceImpl.PlaceInfoWrapper val3 = new TravelToServiceImpl.PlaceInfoWrapper(
                new Trip(), "s", "e");
    }

    /**
     * Method: getRouteByTripId(String tripId, HttpHeaders headers)
     */
    @Test
    public void testGetRouteByTripId() throws Exception {
        try {
            travelToService.getRouteByTripId("1", null);
            travelToService.getRouteByTripId("Z1234", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: getTrainTypeByTripId(String tripId, HttpHeaders headers)
     */
    @Test
    public void testGetTrainTypeByTripId() throws Exception {
        TravelToServiceImpl travelToService = new TravelToServiceImpl();
        try {
            travelToService.getTrainTypeByTripId("1", null);
            travelToService.getTrainTypeByTripId("Z1234", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: getTripByRoute(List<String> routeIds, HttpHeaders headers)
     */
    @Test
    public void testGetTripByRoute() throws Exception {
        TravelToServiceImpl travelToService = new TravelToServiceImpl();
        try {
            List<String> routeIds = new ArrayList<>();
            routeIds.add("1");
            travelToService.getTripByRoute(routeIds, null);
            routeIds.clear();
            routeIds.add("0b23bd3e-876a-4af3-b920-c50a90c90b04");
            travelToService.getTripByRoute(routeIds, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: create(TravelInfo info, HttpHeaders headers)
     */
    @Test
    public void testCreate() throws Exception {
        TravelToServiceImpl travelToService = new TravelToServiceImpl();
        try {
            TravelInfo info = new TravelInfo();
            travelToService.create(info, null);
            info.setTripId("Z1234");
            travelToService.create(info, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: retrieve(String tripId, HttpHeaders headers)
     */
    @Test
    public void testRetrieve() throws Exception {
        TravelToServiceImpl travelToService = new TravelToServiceImpl();
        try {
            travelToService.retrieve("1", null);
            travelToService.retrieve("Z1234", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: update(TravelInfo info, HttpHeaders headers)
     */
    @Test
    public void testUpdate() throws Exception {
        try {
            TravelInfo info =new TravelInfo();
            info.setTripId("myid");
            travelToService.update(info,null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * Method: delete(String tripId, HttpHeaders headers)
     */
    @Test
    public void testDelete() throws Exception {
//TODO: Test goes here...
        try {
            travelToService.delete("myid",null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method: query(TripInfo info, HttpHeaders headers)
     */
    @Test
    public void testQuery() throws Exception {
//TODO: Test goes here...
        try {
            TripInfo ti= new TripInfo();
            ti.setEndPlace("beijing");
            ti.setStartingPlace("shanghai");
            travelToService.query(ti,null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * Method: getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers)
     */
    @Test
    public void testGetTripAllDetailInfo() throws Exception {
//TODO: Test goes here...
        try {
            TripAllDetailInfo ta= new TripAllDetailInfo();
            ta.setTripId("myid");
            ta.setFrom("shanghai");
            ta.setTravelDate(new Date());
            ta.setTo("beijing");
            travelToService.getTripAllDetailInfo(ta,null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

    /**
     * Method: queryAll(HttpHeaders headers)
     */
    @Test
    public void testQueryAll() throws Exception {
        try {
            travelToService.queryAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

//TODO: Test goes here... 
    }

    /**
     * Method: adminQueryAll(HttpHeaders headers)
     */
    @Test
    public void testAdminQueryAll() throws Exception {
//TODO: Test goes here...
        try {
            travelToService.adminQueryAll(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }


    /**
     * Method: getTravelResult(HttpEntity requestEntity)
     */
    @Test
    public void testGetTravelResult() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getTravelResult", HttpEntity.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getSoldTicket(HttpHeaders headers, Trip trip, Date departureTime)
     */
    @Test
    public void testGetSoldTicket() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getSoldTicket", HttpHeaders.class, Trip.class, Date.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getTripResponse(HttpHeaders headers, PlaceInfoWrapper wrapper, Date departureTime)
     */
    @Test
    public void testGetTripResponse() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getTripResponse", HttpHeaders.class, PlaceInfoWrapper.class, Date.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getFinalResponse(HttpHeaders headers, TripResponse response, PlaceInfoWrapper wrapper, TravelResult resultForTravel, SoldTicket result)
     */
    @Test
    public void testGetFinalResponse() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getFinalResponse", HttpHeaders.class, TripResponse.class, PlaceInfoWrapper.class, TravelResult.class, SoldTicket.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getTickets(PlaceInfoWrapper wrapper, Date departureTime, HttpHeaders headers)
     */
    @Test
    public void testGetTickets() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getTickets", PlaceInfoWrapper.class, Date.class, HttpHeaders.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: afterToday(Date date)
     */
    @Test
    public void testAfterToday() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("afterToday", Date.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getTrainType(String trainTypeId, HttpHeaders headers)
     */
    @Test
    public void testGetTrainType() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getTrainType", String.class, HttpHeaders.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: queryForStationId(String stationName, HttpHeaders headers)
     */
    @Test
    public void testQueryForStationId() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("queryForStationId", String.class, HttpHeaders.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getRouteByRouteId(String routeId, HttpHeaders headers)
     */
    @Test
    public void testGetRouteByRouteId() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getRouteByRouteId", String.class, HttpHeaders.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType, HttpHeaders headers)
     */
    @Test
    public void testGetRestTicketNumber() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TravelToServiceImpl.getClass().getMethod("getRestTicketNumber", Date.class, String.class, String.class, String.class, int.class, HttpHeaders.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
