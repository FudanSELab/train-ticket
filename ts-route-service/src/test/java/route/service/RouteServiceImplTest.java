package route.service;

import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import route.entity.Route;
import route.entity.RouteInfo;
import route.repository.RouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class RouteServiceImplTest {

    @InjectMocks
    private RouteServiceImpl routeServiceImpl;

    @Mock
    private RouteRepository routeRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAndModify1() {
        RouteInfo info = new RouteInfo("id", "start_station", "end_station", "shanghai", "5,10");
        Response result = routeServiceImpl.createAndModify(info, headers);
        Assert.assertEquals(new Response<>(0, "Station Number Not Equal To Distance Number", null), result);
    }

    @Test
    public void testCreateAndModify2() {
        RouteInfo info = new RouteInfo("id", "start_station", "end_station", "shanghai", "5");
        Mockito.when(routeRepository.save(Mockito.any(Route.class))).thenReturn(null);
        Response result = routeServiceImpl.createAndModify(info, headers);
        Assert.assertEquals("Save Success", result.getMsg());
    }

    @Test
    public void testCreateAndModify3() {
        RouteInfo info = new RouteInfo("id123456789", "start_station", "end_station", "shanghai", "5");
        Mockito.when(routeRepository.findById(Mockito.anyString())).thenReturn(null);
        Mockito.when(routeRepository.save(Mockito.any(Route.class))).thenReturn(null);
        Response result = routeServiceImpl.createAndModify(info, headers);
        Assert.assertEquals("Modify success", result.getMsg());
    }

    @Test
    public void testDeleteRoute1() {
        Mockito.doNothing().doThrow(new RuntimeException()).when(routeRepository).removeRouteById(Mockito.anyString());
        Mockito.when(routeRepository.findById(Mockito.anyString())).thenReturn(null);
        Response result = routeServiceImpl.deleteRoute("route_id", headers);
        Assert.assertEquals(new Response<>(1, "Delete Success", "route_id"), result);
    }

    @Test
    public void testDeleteRoute2() {
        Route route = new Route();
        Mockito.doNothing().doThrow(new RuntimeException()).when(routeRepository).removeRouteById(Mockito.anyString());
        Mockito.when(routeRepository.findById(Mockito.anyString()).get()).thenReturn(route);
        Response result = routeServiceImpl.deleteRoute("route_id", headers);
        Assert.assertEquals(new Response<>(0, "Delete failed, Reason unKnown with this routeId", "route_id"), result);
    }

    @Test
    public void testGetRouteById1() {
        Mockito.when(routeRepository.findById(Mockito.anyString())).thenReturn(null);
        Response result = routeServiceImpl.getRouteById("route_id", headers);
        Assert.assertEquals(new Response<>(0, "No content with the routeId", null), result);
    }

    @Test
    public void testGetRouteById2() {
        Route route = new Route();
        Mockito.when(routeRepository.findById(Mockito.anyString()).get()).thenReturn(route);
        Response result = routeServiceImpl.getRouteById("route_id", headers);
        Assert.assertEquals(new Response<>(1, "Success", route), result);
    }

    @Test
    public void testGetRouteByStartAndTerminal1() {
        List<String> stations = new ArrayList<>();
        stations.add("shanghai");
        stations.add("nanjing");
        List<Integer> distances = new ArrayList<>();
        distances.add(5);
        distances.add(10);
        Route route = new Route(UUID.randomUUID().toString(), stations, distances, "shanghai", "nanjing");
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route);
        Mockito.when(routeRepository.findAll()).thenReturn(routes);
        Response result = routeServiceImpl.getRouteByStartAndEnd("shanghai", "nanjing", headers);
        Assert.assertEquals("Success", result.getMsg());
    }

    @Test
    public void testGetRouteByStartAndTerminal2() {
        ArrayList<Route> routes = new ArrayList<>();
        Mockito.when(routeRepository.findAll()).thenReturn(routes);
        Response result = routeServiceImpl.getRouteByStartAndEnd("shanghai", "nanjing", headers);
        Assert.assertEquals("No routes with the startId and terminalId", result.getMsg());
    }

    @Test
    public void testGetAllRoutes1() {
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route());
        Mockito.when(routeRepository.findAll()).thenReturn(routes);
        Response result = routeServiceImpl.getAllRoutes(headers);
        Assert.assertEquals("Success", result.getMsg());
    }

    @Test
    public void testGetAllRoutes2() {
        Mockito.when(routeRepository.findAll()).thenReturn(null);
        Response result = routeServiceImpl.getAllRoutes(headers);
        Assert.assertEquals("No Content", result.getMsg());
    }

}
