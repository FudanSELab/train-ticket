package admintravel.service;

import edu.fudan.common.entity.AdminTrip;
import edu.fudan.common.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author fdse
 */
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

        AdminTravelServiceImpl.LOGGER.info("[getAllTravels][Get All Travels]");
        HttpEntity requestEntity = new HttpEntity(headers);
        String travel_service_url = getServiceUrl("ts-travel-service");
        ResponseEntity<Response<ArrayList<AdminTrip>>> re = restTemplate.exchange(
                travel_service_url + "/api/v1/travelservice/admin_trip",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re.getBody();

        if (result.getStatus() == 1) {
            ArrayList<AdminTrip> adminTrips = result.getData();
            AdminTravelServiceImpl.LOGGER.info("[getAllTravels][Get Travel From ts-travel-service successfully!]");
            trips.addAll(adminTrips);
        } else {
            AdminTravelServiceImpl.LOGGER.error("[getAllTravels][receive response][Get Travel From ts-travel-service fail!]");
        }

        HttpEntity requestEntity2 = new HttpEntity(headers);
        String travel2_service_url = getServiceUrl("ts-travel2-service");
        ResponseEntity<Response<ArrayList<AdminTrip>>> re2 = restTemplate.exchange(
                travel2_service_url + "/api/v1/travel2service/admin_trip",
                HttpMethod.GET,
                requestEntity2,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re2.getBody();

        if (result.getStatus() == 1) {
            AdminTravelServiceImpl.LOGGER.info("[getAllTravels][Get Travel From ts-travel2-service successfully!]");
            ArrayList<AdminTrip> adminTrips = result.getData();
            trips.addAll(adminTrips);
        } else {
            AdminTravelServiceImpl.LOGGER.error("[getAllTravels][receive response][Get Travel From ts-travel2-service fail!]");
        }
        result.setData(trips);

        return result;
    }

    @Override
    public Response addTravel(TravelInfo request, HttpHeaders headers) {
        Response result;
        String requestUrl;
        String travel_service_url = getServiceUrl("ts-travel-service");
        String travel2_service_url = getServiceUrl("ts-travel2-service");
        if (request.getTrainTypeName().charAt(0) == 'G' || request.getTrainTypeName().charAt(0) == 'D') {
            requestUrl = travel_service_url + "/api/v1/travelservice/trips";
        } else {
            requestUrl = travel2_service_url + "/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                Response.class);
        result = re.getBody();

        if (result.getStatus() == 1) {
            AdminTravelServiceImpl.LOGGER.info("[addTravel][Admin add new travel][success]");
            return new Response<>(1, "[Admin add new travel]", null);
        } else {
            AdminTravelServiceImpl.LOGGER.error("[addTravel][receive response][Admin add new travel failed][trip id: {}]", request.getTripId());
            return new Response<>(0, "Admin add new travel failed", null);
        }
    }

    @Override
    public Response updateTravel(TravelInfo request, HttpHeaders headers) {
        Response result;

        String requestUrl = "";
        String travel_service_url = getServiceUrl("ts-travel-service");
        String travel2_service_url = getServiceUrl("ts-travel2-service");
        if (request.getTrainTypeName().charAt(0) == 'G' || request.getTrainTypeName().charAt(0) == 'D') {
            requestUrl = travel_service_url + "/api/v1/travelservice/trips";
        } else {
            requestUrl = travel2_service_url + "/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                requestEntity,
                Response.class);

        result = re.getBody();
        if (result.getStatus() != 1)  {
            AdminTravelServiceImpl.LOGGER.info("[updateTravel][Admin update travel failed]");
            return new Response<>(0, "Admin update travel failed", null);
        }

        AdminTravelServiceImpl.LOGGER.info("[updateTravel][Admin update travel][success]");
        return result;
    }

    @Override
    public Response deleteTravel(String tripId, HttpHeaders headers) {

        Response result;
        String requestUtl = "";
        String travel_service_url = getServiceUrl("ts-travel-service");
        String travel2_service_url = getServiceUrl("ts-travel2-service");
        if (tripId.charAt(0) == 'G' || tripId.charAt(0) == 'D') {
            requestUtl = travel_service_url + "/api/v1/travelservice/trips/" + tripId;
        } else {
            requestUtl = travel2_service_url + "/api/v1/travel2service/trips/" + tripId;
        }
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUtl,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);

        result = re.getBody();
        if (result.getStatus() != 1) {
            AdminTravelServiceImpl.LOGGER.error("[deleteTravel][receive response][Admin delete travel failed][trip id: {}]", tripId);
            return new Response<>(0, "Admin delete travel failed", null);
        }

        AdminTravelServiceImpl.LOGGER.info("[deleteTravel][Admin delete travel success][trip id: {}]", tripId);
        return result;
    }
}
