package travelto.service; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* TravelToServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>11ÔÂ 22, 2019</pre> 
* @version 1.0 
*/ 
public class TravelToServiceImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getRouteByTripId(String tripId, HttpHeaders headers) 
* 
*/ 
@Test
public void testGetRouteByTripId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getTrainTypeByTripId(String tripId, HttpHeaders headers) 
* 
*/ 
@Test
public void testGetTrainTypeByTripId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getTripByRoute(List<String> routeIds, HttpHeaders headers) 
* 
*/ 
@Test
public void testGetTripByRoute() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: create(TravelInfo info, HttpHeaders headers) 
* 
*/ 
@Test
public void testCreate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: retrieve(String tripId, HttpHeaders headers) 
* 
*/ 
@Test
public void testRetrieve() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: update(TravelInfo info, HttpHeaders headers) 
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: delete(String tripId, HttpHeaders headers) 
* 
*/ 
@Test
public void testDelete() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: query(TripInfo info, HttpHeaders headers) 
* 
*/ 
@Test
public void testQuery() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers) 
* 
*/ 
@Test
public void testGetTripAllDetailInfo() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: queryAll(HttpHeaders headers) 
* 
*/ 
@Test
public void testQueryAll() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: adminQueryAll(HttpHeaders headers) 
* 
*/ 
@Test
public void testAdminQueryAll() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getTravelResult(HttpEntity requestEntity) 
* 
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
* 
* Method: getSoldTicket(HttpHeaders headers, Trip trip, Date departureTime) 
* 
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
* 
* Method: getTripResponse(HttpHeaders headers, PlaceInfoWrapper wrapper, Date departureTime) 
* 
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
* 
* Method: getFinalResponse(HttpHeaders headers, TripResponse response, PlaceInfoWrapper wrapper, TravelResult resultForTravel, SoldTicket result) 
* 
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
* 
* Method: getTickets(PlaceInfoWrapper wrapper, Date departureTime, HttpHeaders headers) 
* 
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
* 
* Method: afterToday(Date date) 
* 
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
* 
* Method: getTrainType(String trainTypeId, HttpHeaders headers) 
* 
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
* 
* Method: queryForStationId(String stationName, HttpHeaders headers) 
* 
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
* 
* Method: getRouteByRouteId(String routeId, HttpHeaders headers) 
* 
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
* 
* Method: getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType, HttpHeaders headers) 
* 
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
