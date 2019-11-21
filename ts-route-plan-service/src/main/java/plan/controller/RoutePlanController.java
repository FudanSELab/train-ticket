package plan.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import plan.entity.RoutePlanInfo;
import plan.service.RoutePlanService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/routeplanservice")
public class RoutePlanController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoutePlanService routePlanService;

    private static final String NUM = " Num:";
    private static final String DATE = " Date:";

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ RoutePlan Service ] !";
    }

    @PostMapping(value = "/routePlan/cheapestRoute")
    public HttpEntity getCheapestRoutes(@RequestBody RoutePlanInfo info, @RequestHeader HttpHeaders headers) {
        logger.info("[Route Plan Service][Get Cheapest Routes] From:" + info.getFromStationName() +
                " to:" + info.getToStationName() + NUM + info.getNum() + DATE + info.getTravelDate());
        return ok(routePlanService.searchCheapestResult(info, headers));
    }

    @PostMapping(value = "/routePlan/quickestRoute")
    public HttpEntity getQuickestRoutes(@RequestBody RoutePlanInfo info, @RequestHeader HttpHeaders headers) {
        logger.info("[Route Plan Service][Get Quickest Routes] From:" + info.getFromStationName() +
                " to:" + info.getToStationName() + NUM + info.getNum() + DATE + info.getTravelDate());
        return ok(routePlanService.searchQuickestResult(info, headers));
    }

    @PostMapping(value = "/routePlan/minStopStations")
    public HttpEntity getMinStopStations(@RequestBody RoutePlanInfo info, @RequestHeader HttpHeaders headers) {
        logger.info("[Route Plan Service][Get Min Stop Stations] From:" + info.getFromStationName() +
                " to:" + info.getToStationName() + NUM + info.getNum() + DATE + info.getTravelDate());
        return ok(routePlanService.searchMinStopStations(info, headers));
    }
}
