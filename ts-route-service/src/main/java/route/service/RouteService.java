package route.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import route.entity.*;

/**
 * @author cw
 */
public interface RouteService {

    /**
     * get route with id in [from, to]
     * @param startId from
     * @param terminalId to
     * @param headers headers
     * @return response
     */
    Response getRouteByStartAndTerminal(String startId, String terminalId, HttpHeaders headers);

    /**
     * get all routes
     * @param headers headers
     * @return response
     */
    Response getAllRoutes(HttpHeaders headers);

    /**
     * get route by id
     * @param routeId route id
     * @param headers headers
     * @return response
     */
    Response getRouteById(String routeId, HttpHeaders headers);

    /**
     * delete route by id
     * @param routeId id
     * @param headers headers
     * @return response
     */
    Response deleteRoute(String routeId, HttpHeaders headers);

    /**
     * create route and modify
     * @param info info
     * @param headers headers
     * @return response
     */
    Response createAndModify(RouteInfo info, HttpHeaders headers);

}
