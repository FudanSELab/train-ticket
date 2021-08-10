package adminroute.controller;

import adminroute.entity.RouteInfo;
import adminroute.service.AdminRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/adminrouteservice")
public class AdminRouteController {

    @Autowired
    AdminRouteService adminRouteService;

    public static final Logger logger = LoggerFactory.getLogger(AdminRouteController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminRoute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminroute")
    public HttpEntity getAllRoutes(@RequestHeader HttpHeaders headers) {
        logger.info("Get all routes request");
        return ok(adminRouteService.getAllRoutes(headers));
    }

    @PostMapping(value = "/adminroute")
    public HttpEntity addRoute(@RequestBody RouteInfo request, @RequestHeader HttpHeaders headers) {
        logger.info("Create and modify route, route id: {}, from station {} to station {}",
                request.getId(), request.getStartStation(), request.getEndStation());
        return ok(adminRouteService.createAndModifyRoute(request, headers));
    }

    @DeleteMapping(value = "/adminroute/{routeId}")
    public HttpEntity deleteRoute(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        logger.info("Delete route, route id: {}", routeId);
        return ok(adminRouteService.deleteRoute(routeId, headers));
    }


}
