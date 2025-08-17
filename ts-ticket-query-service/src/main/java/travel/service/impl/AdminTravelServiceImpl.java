package travel.service.impl;

import edu.fudan.common.entity.AdminTrip;
import edu.fudan.common.entity.Route;
import edu.fudan.common.entity.TrainType;
import edu.fudan.common.entity.TravelInfo;
import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travel.service.AdminTravelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminTravelServiceImpl implements AdminTravelService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTravelServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response getAllTravels(HttpHeaders headers) {
        Response<ArrayList<AdminTrip>> result;
        ArrayList<AdminTrip> trips = new ArrayList<>();
        LOGGER.info("[getAllTravels][Get All Travels]");
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String travel_service_url = getServiceUrl("ts-ticket-query-service");
        ResponseEntity<Response<ArrayList<AdminTrip>>> re = restTemplate.exchange(
                travel_service_url + "/api/v1/ticketquery/admin_trip",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {});
        result = re.getBody();
        if (result.getStatus() == 1) {
            trips.addAll(result.getData());
            LOGGER.info("[getAllTravels][Get Travel From ts-ticket-query-service successfully!]");
        } else {
            LOGGER.error("[getAllTravels][Get Travel From ts-ticket-query-service fail!]");
        }
        result.setData(trips);
        return result;
    }

    @Override
    public Response addTravel(TravelInfo request, HttpHeaders headers) {
        Response response = checkTravelInfo(request, headers);
        if (response.getStatus() == 0) {
            return response;
        }
        String tripId = request.getTripId();
        String serviceUrl = getServiceUrl("ts-travel-service");
        String url = serviceUrl + "/api/v1/ticketquery/trips";
        HttpEntity<TravelInfo> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response.class);
        if (re.getBody() != null && re.getBody().getStatus() == 1) {
            LOGGER.info("[addTravel][Admin add new travel][success]");
            return new Response<>(1, "Admin add new travel", null);
        }
        LOGGER.error("[addTravel][Admin add new travel failed][trip id: {}]", tripId);
        return new Response<>(0, "Admin add new travel failed", null);
    }

    @Override
    public Response updateTravel(TravelInfo request, HttpHeaders headers) {
        Response response = checkTravelInfo(request, headers);
        if (response.getStatus() == 0) {
            return response;
        }
        String tripId = request.getTripId();
        String serviceUrl = getServiceUrl("ts-travel-service");
        String url = serviceUrl + "/api/v1/ticketquery/trips";
        HttpEntity<TravelInfo> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Response.class);
        if (re.getBody() != null && re.getBody().getStatus() == 1) {
            LOGGER.info("[updateTravel][Admin update travel][success]");
            return re.getBody();
        }
        LOGGER.error("[updateTravel][Admin update travel failed]");
        return new Response<>(0, "Admin update travel failed", null);
    }

    @Override
    public Response deleteTravel(String tripId, HttpHeaders headers) {
        String serviceUrl = getServiceUrl("ts-travel-service");
        String url = serviceUrl + "/api/v1/ticketquery/trips/" + tripId;
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> re = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Response.class);
        if (re.getBody() != null && re.getBody().getStatus() == 1) {
            LOGGER.info("[deleteTravel][Admin delete travel success][trip id: {}]", tripId);
            return re.getBody();
        }
        LOGGER.error("[deleteTravel][Admin delete travel failed][trip id: {}]", tripId);
        return new Response<>(0, "Admin delete travel failed", null);
    }

    /* Validation helpers copied, unchanged */
    private Response checkTravelInfo(TravelInfo info, HttpHeaders headers) {
        String start = info.getStartStationName();
        String end = info.getTerminalStationName();
        List<String> stations = new ArrayList<>();
        stations.add(start);
        stations.add(end);
        Response stationCheck = checkStationsExists(stations, headers);
        if (stationCheck.getStatus() == 0) {
            return stationCheck;
        }
        TrainType trainType = queryTrainTypeByName(info.getTrainTypeName(), headers);
        if (trainType == null) {
            return new Response<>(0, "Train type doesn't exist", null);
        }
        Route route = getRouteByRouteId(info.getRouteId(), headers);
        if (route == null) {
            return new Response<>(0, "Route doesn't exist", null);
        }
        if (!route.getStations().contains(start) || !route.getStations().contains(end) || route.getStations().indexOf(start) >= route.getStations().indexOf(end)) {
            return new Response<>(0, "Station not correct in Route", null);
        }
        return new Response<>(1, "check success", null);
    }

    private Response checkStationsExists(List<String> stationNames, HttpHeaders headers) {
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(stationNames, null);
        String station_service_url = getServiceUrl("ts-station-service");
        ResponseEntity<Response> re = restTemplate.exchange(station_service_url + "/api/v1/stationservice/stations/idlist", HttpMethod.POST, requestEntity, Response.class);
        return re.getBody();
    }

    private TrainType queryTrainTypeByName(String trainTypeName, HttpHeaders headers) {
        String train_service_url = getServiceUrl("ts-train-service");
        ResponseEntity<Response> re = restTemplate.exchange(train_service_url + "/api/v1/trainservice/trains/byName/" + trainTypeName, HttpMethod.GET, new HttpEntity<>(null), Response.class);
        return JsonUtils.conveterObject(re.getBody().getData(), TrainType.class);
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        String route_service_url = getServiceUrl("ts-route-plan-service");
        ResponseEntity<Response> re = restTemplate.exchange(route_service_url + "/api/v1/routeservice/routes/" + routeId, HttpMethod.GET, new HttpEntity<>(null), Response.class);
        if (re.getBody().getStatus() == 0) {
            return null;
        }
        return JsonUtils.conveterObject(re.getBody().getData(), Route.class);
    }
}
