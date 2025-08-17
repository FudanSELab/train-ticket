package route.service.impl;

import edu.fudan.common.entity.Route;
import edu.fudan.common.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import route.service.AdminRouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRouteServiceImpl implements AdminRouteService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRouteServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response getAllRoutes(HttpHeaders headers) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response> re = restTemplate.exchange(route_service_url + "/api/v1/routeservice/routes", HttpMethod.GET, requestEntity, Response.class);
        return re.getBody();
    }

    @Override
    public Response createAndModifyRoute(RouteInfo request, HttpHeaders headers) {
        // simple pass-through to route-service
        HttpEntity<RouteInfo> requestEntity = new HttpEntity<>(request, headers);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response<Route>> re = restTemplate.exchange(route_service_url + "/api/v1/routeservice/routes", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Response<Route>>(){});
        return re.getBody();
    }

    @Override
    public Response deleteRoute(String routeId, HttpHeaders headers) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response> re = restTemplate.exchange(route_service_url + "/api/v1/routeservice/routes/" + routeId, HttpMethod.DELETE, requestEntity, Response.class);
        return re.getBody();
    }
}
