package route.service;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import route.entity.Route;
import route.entity.RouteInfo;
import route.repository.RouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

    String success = "Success";

    @Override
    public Response createAndModify(RouteInfo info, HttpHeaders headers) {
        RouteServiceImpl.LOGGER.info("Create And Modify Start: {} End: {}", info.getStartStation(), info.getEndStation());

        String[] stations = info.getStationList().split(",");
        String[] distances = info.getDistanceList().split(",");
        List<String> stationList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();
        if (stations.length != distances.length) {
            RouteServiceImpl.LOGGER.error("Create and modify error.Station number not equal to distance number,RouteId: {}",info.getId());
            return new Response<>(0, "Station Number Not Equal To Distance Number", null);
        }
        for (int i = 0; i < stations.length; i++) {
            stationList.add(stations[i]);
            distanceList.add(Integer.parseInt(distances[i]));
        }
        int maxIdArrayLen = 10;
        if (info.getId() == null || info.getId().length() < maxIdArrayLen) {
            Route route = new Route();
            route.setId(UUID.randomUUID().toString());
            route.setStartStationId(info.getStartStation());
            route.setTerminalStationId(info.getEndStation());
            route.setStations(stationList);
            route.setDistances(distanceList);
            routeRepository.save(route);
            RouteServiceImpl.LOGGER.info("Save success");

            return new Response<>(1, "Save Success", route);
        } else {
            Route route = routeRepository.findById(info.getId());
            if (route == null) {
                route = new Route();
                route.setId(info.getId());
            }

            route.setStartStationId(info.getStartStation());
            route.setTerminalStationId(info.getEndStation());
            route.setStations(stationList);
            route.setDistances(distanceList);
            routeRepository.save(route);
            RouteServiceImpl.LOGGER.info("Modify success");
            return new Response<>(1, "Modify success", route);
        }
    }

    @Override
    public Response deleteRoute(String routeId, HttpHeaders headers) {
        routeRepository.removeRouteById(routeId);
        Route route = routeRepository.findById(routeId);
        if (route == null) {
            return new Response<>(1, "Delete Success", routeId);
        } else {
            RouteServiceImpl.LOGGER.error("Delete error.Route not found,RouteId: {}",routeId);
            return new Response<>(0, "Delete failed, Reason unKnown with this routeId", routeId);
        }
    }

    @Override
    public Response getRouteById(String routeId, HttpHeaders headers) {
        Route route = routeRepository.findById(routeId);
        if (route == null) {
            RouteServiceImpl.LOGGER.error("Find route error.Route not found,RouteId: {}",routeId);
            return new Response<>(0, "No content with the routeId", null);
        } else {
            return new Response<>(1, success, route);
        }

    }

    @Override
    public Response getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers) {
        ArrayList<Route> routes = routeRepository.findAll();
        RouteServiceImpl.LOGGER.info("Find All: {}", routes.size());
        List<Route> resultList = new ArrayList<>();
        for (Route route : routes) {
            if (route.getStations().contains(startId) &&
                    route.getStations().contains(terminalId) &&
                    route.getStations().indexOf(startId) < route.getStations().indexOf(terminalId)) {
                resultList.add(route);
            }
        }
        if (!resultList.isEmpty()) {
            return new Response<>(1, success, resultList);
        } else {
            RouteServiceImpl.LOGGER.warn("Find by start and terminal warn.Routes not found,startId: {},terminalId: {}",startId,terminalId);
            return new Response<>(0, "No routes with the startId and terminalId", null);
        }
    }

    @Override
    public Response getAllRoutes(HttpHeaders headers) {
        ArrayList<Route> routes = routeRepository.findAll();
        if (routes != null && !routes.isEmpty()) {
            return new Response<>(1, success, routes);
        } else {
            RouteServiceImpl.LOGGER.warn("Find all routes warn: {}","No Content");
            return new Response<>(0, "No Content", null);
        }
    }

}