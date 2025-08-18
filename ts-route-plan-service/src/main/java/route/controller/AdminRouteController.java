package route.controller;

import edu.fudan.common.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import route.service.AdminRouteService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/route-plan/admin")
public class AdminRouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRouteController.class);

    @Autowired
    private AdminRouteService adminRouteService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminRoute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/adminroute")
    public ResponseEntity<Response> getAllRoutes(@RequestHeader HttpHeaders headers) {
        LOGGER.info("[getAllRoutes][Get all routes]");
        return ok(adminRouteService.getAllRoutes(headers));
    }

    @PostMapping("/adminroute")
    public ResponseEntity<Response> addRoute(@RequestBody RouteInfo request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addRoute][Create route][route id: {}]", request.getId());
        return ok(adminRouteService.createAndModifyRoute(request, headers));
    }

    @DeleteMapping("/adminroute/{routeId}")
    public ResponseEntity<Response> deleteRoute(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[deleteRoute][Delete route][route id: {}]", routeId);
        return ok(adminRouteService.deleteRoute(routeId, headers));
    }
}
