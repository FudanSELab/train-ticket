package admintravel.service;

import edu.fudan.common.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface AdminTravelService {

    /**
     * get all travels
     *
     * @param headers headers
     * @return Response
     */
    Response getAllTravels(  HttpHeaders headers);

    /**
     * add travel by traver information
     *
     * @param request request
     * @param headers headers
     * @return Response
     */
    Response addTravel(TravelInfo request, HttpHeaders headers);

    /**
     * update travel by traver information
     *
     * @param request request
     * @param headers headers
     * @return Response
     */
    Response updateTravel(TravelInfo request, HttpHeaders headers);

    /**
     * add travel by trip id
     *
     * @param tripId trip id
     * @param headers headers
     * @return Response
     */
    Response deleteTravel(String tripId, HttpHeaders headers);
}
