package route.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import route.entity.*;

import java.util.List;


/**
 * @author fdse
 */
public interface RouteService {

    /**
     * get route with id
     *
     * @param startId start station id
     * @param terminalId terminal station id
     * @param headers headers
     * @return Response
     */
    Response getRouteByStartAndEnd(String startId, String terminalId, HttpHeaders headers);

    /**
     * get all routes
     *
     * @param headers headers
     * @return Response
     */
    Response getAllRoutes(HttpHeaders headers);

    /**
     * get route by id
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response getRouteById(String routeId, HttpHeaders headers);

    /**
     * get route by ids
     *
     * @param routeIds route ids
     * @param headers headers
     * @return Response
     */
    Response getRouteByIds(List<String> routeIds, HttpHeaders headers);

    /**
     * delete route by id
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response deleteRoute(String routeId, HttpHeaders headers);

    /**
     * create route and modify
     *
     * @param info info
     * @param headers headers
     * @return Response
     */
    Response createAndModify(RouteInfo info, HttpHeaders headers);

}
