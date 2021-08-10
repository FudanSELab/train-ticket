package admintravel.service;

import admintravel.entity.AdminTrip;
import admintravel.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTravelServiceImpl.class);

    @Override
    public Response getAllTravels(HttpHeaders headers) {
        Response<ArrayList<AdminTrip>> result;
        ArrayList<AdminTrip> trips = new ArrayList<>();

        AdminTravelServiceImpl.LOGGER.info("[Get All Travels]");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<ArrayList<AdminTrip>>> re = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/admin_trip",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re.getBody();

        if (result.getStatus() == 1) {
            ArrayList<AdminTrip> adminTrips = result.getData();
            AdminTravelServiceImpl.LOGGER.info("[Get Travel From ts-travel-service successfully!]");
            trips.addAll(adminTrips);
        } else {
            AdminTravelServiceImpl.LOGGER.error("[Get Travel From ts-travel-service fail!]");
        }

        HttpEntity requestEntity2 = new HttpEntity(headers);
        ResponseEntity<Response<ArrayList<AdminTrip>>> re2 = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/admin_trip",
                HttpMethod.GET,
                requestEntity2,
                new ParameterizedTypeReference<Response<ArrayList<AdminTrip>>>() {
                });
        result = re2.getBody();

        if (result.getStatus() == 1) {
            AdminTravelServiceImpl.LOGGER.info("[Get Travel From ts-travel2-service successfully!]");
            ArrayList<AdminTrip> adminTrips = result.getData();
            trips.addAll(adminTrips);
        } else {
            AdminTravelServiceImpl.LOGGER.error("[Get Travel From ts-travel2-service fail!]");
        }
        result.setData(trips);

        return result;
    }

    @Override
    public Response addTravel(TravelInfo request, HttpHeaders headers) {
        Response result;
        String requestUrl;
        if (request.getTrainTypeId().charAt(0) == 'G' || request.getTrainTypeId().charAt(0) == 'D') {
            requestUrl = "http://ts-travel-service:12346/api/v1/travelservice/trips";
        } else {
            requestUrl = "http://ts-travel2-service:16346/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestEntity,
                Response.class);
        result = re.getBody();

        if (result.getStatus() == 1) {
            AdminTravelServiceImpl.LOGGER.info("Admin add new travel");
            return new Response<>(1, "[Admin add new travel]", null);
        } else {
            AdminTravelServiceImpl.LOGGER.error("Admin add new travel failed, trip id: {}", request.getTripId());
            return new Response<>(0, "Admin add new travel failed", null);
        }
    }

    @Override
    public Response updateTravel(TravelInfo request, HttpHeaders headers) {
        Response result;

        String requestUrl = "";
        if (request.getTrainTypeId().charAt(0) == 'G' || request.getTrainTypeId().charAt(0) == 'D') {
            requestUrl = "http://ts-travel-service:12346/api/v1/travelservice/trips";
        } else {
            requestUrl = "http://ts-travel2-service:16346/api/v1/travel2service/trips";
        }
        HttpEntity requestEntity = new HttpEntity(request, headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                requestEntity,
                Response.class);

        result = re.getBody();
        if (result.getStatus() != 1)  {
            AdminTravelServiceImpl.LOGGER.info("[Admin update travel failed]");
            return new Response<>(0, "Admin update travel failed", null);
        }

        AdminTravelServiceImpl.LOGGER.info("[Admin update travel success]");
        return result;
    }

    @Override
    public Response deleteTravel(String tripId, HttpHeaders headers) {

        Response result;
        String requestUtl = "";
        if (tripId.charAt(0) == 'G' || tripId.charAt(0) == 'D') {
            requestUtl = "http://ts-travel-service:12346/api/v1/travelservice/trips/" + tripId;
        } else {
            requestUtl = "http://ts-travel2-service:16346/api/v1/travel2service/trips/" + tripId;
        }
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                requestUtl,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);

        result = re.getBody();
        if (result.getStatus() != 1) {
            AdminTravelServiceImpl.LOGGER.error("Admin delete travel failed, trip id: {}", tripId);
            return new Response<>(0, "Admin delete travel failed", null);
        }

        AdminTravelServiceImpl.LOGGER.info("Admin delete travel success, trip id: {}", tripId);
        return result;
    }
}
