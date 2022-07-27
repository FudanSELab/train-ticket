package adminroute.service;

import edu.fudan.common.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * @author fdse
 */
public interface AdminRouteService {

    /**
     * get all routes with headers
     *
     * @param headers headers
     * @return Response
     */
    Response getAllRoutes(HttpHeaders headers);

    /**
     * get all routes with headers
     *
     * @param request route information
     * @param headers headers
     * @return Response
     */
    Response createAndModifyRoute(RouteInfo request, HttpHeaders headers);

    /**
     * get all routes with headers
     *
     * @param routeId route id
     * @param headers headers
     * @return Response
     */
    Response deleteRoute(String routeId, HttpHeaders headers);
}
