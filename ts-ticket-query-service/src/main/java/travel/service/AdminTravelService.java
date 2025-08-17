package travel.service;

import edu.fudan.common.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface AdminTravelService {
    Response getAllTravels(HttpHeaders headers);
    Response addTravel(TravelInfo request, HttpHeaders headers);
    Response updateTravel(TravelInfo request, HttpHeaders headers);
    Response deleteTravel(String tripId, HttpHeaders headers);
}
