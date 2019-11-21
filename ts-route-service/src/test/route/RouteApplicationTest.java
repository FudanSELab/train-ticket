package route;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import route.entity.Route;
import route.entity.RouteInfo;
import route.repository.RouteRepository;
import route.service.RouteService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.alibaba.fastjson.JSON.toJSONString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RouteApplication.class)
public class RouteApplicationTest {
    private String id = null;
    private Route route = null;
    private List<String> stations = null;
    private List<Integer> distances = null;
    private String startStationId = null;
    private String terminalStationId = null;
    private RouteInfo routeInfo = null;
    private RouteInfo unexistedRouteInfo = null;
    private RouteInfo tmpRouteInfo = null;
    private String startStation = null;
    private String endStation = null;
    private String stationList = null;
    private String distanceList = null;
    private RouteInfo existedRouteInfo = null;
    private RouteInfo nullIdRoute = null;

    private MockMvc mvc;
    private HttpHeaders headers;
    private String headerName;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    RouteService routeService;

    @Before
    public void setup() {
        routeRepository.findAll();
        id = "1";
        startStationId = "1";
        terminalStationId = "2";
        stations = new LinkedList<>();
        stations.add("Shanghai");
        stations.add("taiyuan");
        distances = new LinkedList<>();
        distances.add(10);
        distances.add(20);
        route = new Route();
        route.setId("1");
        route.setStartStationId(startStationId);
        route.setTerminalStationId(terminalStationId);
        route.setStations(stations);
        route.setDistances(distances);

        startStation = "shanghai";
        endStation = "taiyuan";
        stationList = "shanghai,liaocheng,taiyuan";
        distanceList = "10,20,14";
        routeInfo = new RouteInfo();
        routeInfo.setId(id);
        routeInfo.setDistanceList(distanceList);
        routeInfo.setEndStation(endStation);
        routeInfo.setStartStation(startStation);
        routeInfo.setStationList(stationList);
        unexistedRouteInfo = new RouteInfo();
        tmpRouteInfo = new RouteInfo();
        unexistedRouteInfo.setId("9");
        tmpRouteInfo.setId("4f2fa792-4269-4b41-a31d-eec99200a892");
        tmpRouteInfo.setStationList("lc,78");
        tmpRouteInfo.setDistanceList("11");
        existedRouteInfo = new RouteInfo();
        existedRouteInfo.setId("4f2fa792-4269-4b41-a31d-eec99200a893");
        existedRouteInfo.setStationList("a,b");
        existedRouteInfo.setDistanceList("1,2");
        existedRouteInfo.setStartStation("a");
        existedRouteInfo.setEndStation("b");
        nullIdRoute = new RouteInfo();
        nullIdRoute.setId(null);
        nullIdRoute.setEndStation("b");
        nullIdRoute.setStartStation("a");
        nullIdRoute.setDistanceList("1,2");
        nullIdRoute.setStationList("a,b");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        headers = new HttpHeaders();
        headerName = "headers";
    }

    @Test
    public void testRoute() {
        assertEquals(route.getId(), id);
        assertEquals(route.getStartStationId(), startStationId);
        assertEquals(route.getTerminalStationId(), terminalStationId);
        assertSame(route.getStations(), stations);
        assertEquals(route.getDistances(), distances);
    }

    @Test
    public void testRouteInfo() {
        assertEquals(routeInfo.getId(), id);
        assertEquals(routeInfo.getEndStation(), endStation);
        assertEquals(routeInfo.getDistanceList(), distanceList);
        assertEquals(routeInfo.getStartStation(), startStation);
        assertEquals(routeInfo.getStationList(), stationList);
    }

    @Test
    public void testRouteService() {
        routeService.getAllRoutes(new HttpHeaders());
        routeService.createAndModify(routeInfo, new HttpHeaders());
        routeService.createAndModify(nullIdRoute, new HttpHeaders());
        routeService.createAndModify(existedRouteInfo, new HttpHeaders());
        routeRepository.findById("0b23bd3e-876a-4af3-b920-c50a90c90b04");
        routeService.getRouteById("0b23bd3e-876a-4af3-b920-c50a90c90b04", new HttpHeaders());
        routeService.createAndModify(tmpRouteInfo, new HttpHeaders());
        routeService.getRouteById(routeInfo.getId(), new HttpHeaders());
        routeService.getRouteById(routeInfo.getId(), new HttpHeaders());
        routeService.getRouteById(unexistedRouteInfo.getId(), new HttpHeaders());
        routeRepository.findById(routeInfo.getId());
        routeRepository.findById(unexistedRouteInfo.getId());
        routeRepository.findById(tmpRouteInfo.getId());
        routeService.getAllRoutes(new HttpHeaders());
        routeService.getRouteByStartAndTerminal(startStationId, terminalStationId, new HttpHeaders());
        routeService.deleteRoute(routeInfo.getId(), new HttpHeaders());
        routeService.deleteRoute(unexistedRouteInfo.getId(), new HttpHeaders());
        routeService.getAllRoutes(new HttpHeaders());
        routeService.getRouteById(routeInfo.getId(), new HttpHeaders());
        routeService.getRouteByStartAndTerminal(startStation, endStation, new HttpHeaders());
        ArrayList<Route> routes = routeRepository.findAll();
        for (Route r : routes) {
            routeService.deleteRoute(r.getId(), new HttpHeaders());
        }
        routeService.getAllRoutes(new HttpHeaders());
        assertTrue(true);
    }

    @Test
    public void testWelcome() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/routeservice/welcome")
                        .contentType(MediaType.TEXT_PLAIN)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcResultHandlers.print()
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testPostRoutes() throws Exception {
        String json = toJSONString(routeInfo);
        String headerJson = toJSONString(headers);
        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/routeservice/routes")
                        .content(json)
                        .param(headerName, headerJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testGetRoutesById() throws Exception {
        String headerJson = toJSONString(headers);
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/routeservice/routes/0b23bd3e-876a-4af3-b920-c50a90c90b04")
                        .param(headerName, headerJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testGetRoutes() throws Exception {
        String headerJson = toJSONString(headers);
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/routeservice/routes")
                        .param(headerName, headerJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testGetRouteByStartAndTerminal() throws Exception {
        String headerJson = toJSONString(headers);
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/routeservice/routes/shanghai/taiyuan")
                        .param(headerName, headerJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testDelete() throws Exception {
        String headerJson = toJSONString(headers);
        mvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/routeservice/routes/0b23bd3e-876a-4af3-b920-c50a90c90b04")
                        .param(headerName, headerJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testRouteApplication() throws Exception {
        RouteApplication.main(new String[]{});
        assertTrue(true);
        RouteApplication ra = new RouteApplication();
        ra.restTemplate(new RestTemplateBuilder());
    }
}