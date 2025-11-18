package route.controller;

import edu.fudan.common.entity.RouteInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import route.service.RouteService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/route-plan/admin")
public class AdminRouteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRouteController.class);

    @Autowired
    private RouteService routeService;

    @PostMapping("/routes")
    public ResponseEntity<Response> addRoute(@RequestBody RouteInfo request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addRoute][Create route][route id: {}]", request.getId());
        return ok(routeService.createAndModifyRoute(request, headers));
    }

    @PutMapping("/routes")
    public ResponseEntity<Response> updateRoute(@RequestBody RouteInfo request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addRoute][Create route][route id: {}]", request.getId());
        return ok(routeService.createAndModifyRoute(request, headers));
    }

    @DeleteMapping("/routes/{routeId}")
    public ResponseEntity<Response> deleteRoute(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[deleteRoute][Delete route][route id: {}]", routeId);
        return ok(routeService.deleteRoute(routeId, headers));
    }
}
