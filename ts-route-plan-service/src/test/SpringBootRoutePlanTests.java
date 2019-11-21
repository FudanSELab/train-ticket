import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import plan.RoutePlanApplication;
import plan.entity.Route;
import plan.entity.RoutePlanInfo;
import plan.entity.RoutePlanResultUnit;
import plan.entity.Trip;
import plan.entity.TripAllDetail;
import plan.entity.TripAllDetailInfo;
import plan.entity.TripId;
import plan.entity.TripInfo;
import plan.entity.TripResponse;
import plan.entity.Type;
import plan.service.RoutePlanService;
import plan.service.RoutePlanServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoutePlanApplication.class)
public class SpringBootRoutePlanTests {
    private String number;
    private String startingPlace;
    private String endPlace;
    private Date startingTime;
    private Date endTime;
    private String trainTypeId;
    private int economyClass;
    private int confortClass;
    private String priceForEconomyClass;
    private String priceForConfortClass;
    private List<String> stations;
    private List<Integer> distances;
    private String startStationId;
    private String endStationId;


    private Type type;
    private TripId tripId;
    private TripInfo tripInfo;
    private TripResponse tripResponse;
    private Trip trip;
    private TripAllDetail tripAllDetail;
    private TripAllDetailInfo tripAllDetailInfo;
    private Route route;
    private RoutePlanInfo routePlanInfo;
    private RoutePlanResultUnit routePlanResultUnit;

    private MockMvc mvc;
    private HttpHeaders header;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    RoutePlanService service;

    @Before
    public void setup(){
        number = "1";
        startingPlace = "Shang Hai";
        endPlace = "Tai Yuan";
        startStationId = "01";
        endStationId = "02";
        startingTime = new Date(2019-1900, 12-1, 20);
        endTime = new Date(2019-1900, 12-1, 20);
        economyClass = 2;
        confortClass = 1;
        priceForEconomyClass = "25";
        priceForConfortClass = "125";
        stations = new ArrayList<>();
        stations.add("");
        distances = new ArrayList<>();
        distances.add(123);


        type = Type.G;
        type.setName("G");
        type.setIndex(1);
        trainTypeId = "GaoTieOne";

        tripId = new TripId();
        tripId = new TripId("G111");
        tripId = new TripId("D111");
        tripId = new TripId("Z111");
        tripId = new TripId("T111");
        tripId = new TripId("K111");
        tripId.setType(type);
        tripId.setNumber(number);

        tripInfo = new TripInfo();
        tripInfo.setStartingPlace(startingPlace);
        tripInfo.setEndPlace(endPlace);
        tripInfo.setDepartureTime(startingTime);

        tripResponse = new TripResponse();
        tripResponse.setTrainTypeId(trainTypeId);
        tripResponse.setTripId(tripId);
        tripResponse.setStartingStation(startingPlace);
        tripResponse.setTerminalStation(endPlace);
        tripResponse.setStartingTime(startingTime);
        tripResponse.setEndTime(endTime);
        tripResponse.setEconomyClass(economyClass);
        tripResponse.setConfortClass(confortClass);
        tripResponse.setPriceForEconomyClass(priceForEconomyClass);
        tripResponse.setPriceForConfortClass(priceForConfortClass);

        route = new Route();
        route.setId(number);
        route.setStations(stations);
        route.setDistances(distances);
        route.setStartStationId(startStationId);
        route.setTerminalStationId(endStationId);

        routePlanInfo = new RoutePlanInfo();
        routePlanInfo = new RoutePlanInfo(startingPlace, endPlace, startingTime, 1);
        routePlanInfo.setFromStationName(startingPlace);
        routePlanInfo.setToStationName(endPlace);
        routePlanInfo.setTravelDate(startingTime);
        routePlanInfo.setNum(1);

        routePlanResultUnit = new RoutePlanResultUnit();
        routePlanResultUnit.setTripId(tripId.toString());
        routePlanResultUnit.setTrainTypeId(trainTypeId);
        routePlanResultUnit.setFromStationName(startingPlace);
        routePlanResultUnit.setToStationName(endPlace);
        routePlanResultUnit.setStopStations(stations);
        routePlanResultUnit.setPriceForSecondClassSeat(priceForEconomyClass);
        routePlanResultUnit.setPriceForFirstClassSeat(priceForConfortClass);
        routePlanResultUnit.setStartingTime(startingTime);
        routePlanResultUnit.setEndTime(endTime);

        trip = new Trip();
        trip = new Trip(tripId, trainTypeId, route.getId());
        trip = new Trip(tripId, trainTypeId, startStationId, startStationId + endStationId, endStationId, startingTime, endTime);
        trip.setTripId(tripId);
        trip.setTrainTypeId(trainTypeId);
        trip.setStartingStationId(startStationId);
        trip.setStationsId(startStationId + endStationId);
        trip.setTerminalStationId(endStationId);
        trip.setStartingTime(startingTime);
        trip.setEndTime(endTime);
        trip.setRouteId(route.getId());

        tripAllDetail = new TripAllDetail();
        tripAllDetail.setTrip(trip);
        tripAllDetail.setStatus(true);
        tripAllDetail.setMessage(number);
        tripAllDetail.setTripResponse(tripResponse);

        tripAllDetailInfo = new TripAllDetailInfo();
        tripAllDetailInfo.setTripId(tripId + "");
        tripAllDetailInfo.setFrom(startingPlace);
        tripAllDetailInfo.setTo(endPlace);
        tripAllDetailInfo.setTravelDate(startingTime);


        mvc = MockMvcBuilders.webAppContextSetup(context)
                //.apply(springSecurity())
                .build();
        header = new HttpHeaders();
    }

    @Test
    public void testEntity() {
        Assert.assertEquals(type.getName(), "G");
        Assert.assertEquals(type.getIndex(), 1);
        Assert.assertEquals(Type.getName(1), "G");
        Assert.assertEquals(Type.getName(8), null);

        Assert.assertEquals(tripId.getType(), type);
        Assert.assertEquals(tripId.getNumber(), number);

        Assert.assertEquals(tripInfo.getStartingPlace(), startingPlace);
        Assert.assertEquals(tripInfo.getEndPlace(), endPlace);
        Assert.assertEquals(tripInfo.getDepartureTime(), startingTime);

        Assert.assertEquals(tripResponse.getTrainTypeId(), trainTypeId);
        Assert.assertEquals(tripResponse.getTripId(), tripId);
        Assert.assertEquals(tripResponse.getStartingStation(), startingPlace);
        Assert.assertEquals(tripResponse.getTerminalStation(), endPlace);
        Assert.assertEquals(tripResponse.getStartingTime(), startingTime);
        Assert.assertEquals(tripResponse.getEndTime(), endTime);
        Assert.assertEquals(tripResponse.getEconomyClass(), economyClass);
        Assert.assertEquals(tripResponse.getConfortClass(), confortClass);
        Assert.assertEquals(tripResponse.getPriceForEconomyClass(), priceForEconomyClass);
        Assert.assertEquals(tripResponse.getPriceForConfortClass(), priceForConfortClass);

        Assert.assertEquals(route.getId(), number);
        Assert.assertEquals(route.getStations(), stations);
        Assert.assertEquals(route.getDistances(), distances);
        Assert.assertEquals(route.getStartStationId(), startStationId);
        Assert.assertEquals(route.getTerminalStationId(), endStationId);

        Assert.assertEquals(trip.getTripId(), tripId);
        Assert.assertEquals(trip.getTrainTypeId(), trainTypeId);
        Assert.assertEquals(trip.getStartingStationId(), startStationId);
        Assert.assertEquals(trip.getStationsId(), startStationId + endStationId);
        Assert.assertEquals(trip.getTerminalStationId(), endStationId);
        Assert.assertEquals(trip.getStartingTime(), startingTime);
        Assert.assertEquals(trip.getEndTime(), endTime);
        Assert.assertEquals(trip.getRouteId(), route.getId());

        Assert.assertEquals(routePlanInfo.getFromStationName(), startingPlace);
        Assert.assertEquals(routePlanInfo.getToStationName(), endPlace);
        Assert.assertEquals(routePlanInfo.getTravelDate(), startingTime);
        Assert.assertEquals(routePlanInfo.getNum(), 1);

        Assert.assertEquals(routePlanResultUnit.getTripId(), tripId.toString());
        Assert.assertEquals(routePlanResultUnit.getTrainTypeId(), trainTypeId);
        Assert.assertEquals(routePlanResultUnit.getFromStationName(), startingPlace);
        Assert.assertEquals(routePlanResultUnit.getToStationName(), endPlace);
        Assert.assertEquals(routePlanResultUnit.getStopStations(), stations);
        Assert.assertEquals(routePlanResultUnit.getPriceForSecondClassSeat(), priceForEconomyClass);
        Assert.assertEquals(routePlanResultUnit.getPriceForFirstClassSeat(), priceForConfortClass);
        Assert.assertEquals(routePlanResultUnit.getStartingTime(), startingTime);
        Assert.assertEquals(routePlanResultUnit.getEndTime(), endTime);

        Assert.assertEquals(tripAllDetail.getTrip(), trip);
        Assert.assertTrue(tripAllDetail.isStatus());
        Assert.assertEquals(tripAllDetail.getMessage(), number);
        Assert.assertEquals(tripAllDetail.getTripResponse(), tripResponse);

        Assert.assertEquals(tripAllDetailInfo.getTripId(), tripId + "");
        Assert.assertEquals(tripAllDetailInfo.getFrom(), startingPlace);
        Assert.assertEquals(tripAllDetailInfo.getTo(), endPlace);
        Assert.assertEquals(tripAllDetailInfo.getTravelDate(), startingTime);
    }

    @Test
    public void testRoutePlanService() {
        service.searchCheapestResult(routePlanInfo, new HttpHeaders());
        service.searchQuickestResult(routePlanInfo, new HttpHeaders());
        service.searchMinStopStations(routePlanInfo, new HttpHeaders());
    }

    @Test
    public void testGetWelcome() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/routeplanservice/welcome")
                .contentType(MediaType.TEXT_PLAIN)
        )       .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN))
                .andDo(MockMvcResultHandlers.print())////打印出请求和相应的内容
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testGetCheapestRoutes() throws Exception {
        String json = JSONObject.toJSONString(routePlanInfo);
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/routeplanservice/routePlan/cheapestRoute")
                .content(json)
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void testGetQuickestRoutes() throws Exception {
        String json = JSONObject.toJSONString(routePlanInfo);
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/routeplanservice/routePlan/quickestRoute")
                .content(json)
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void testGetMinStopStations() throws Exception {
        String json = JSONObject.toJSONString(routePlanInfo);
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/routeplanservice/routePlan/minStopStations")
                .content(json)
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }
}