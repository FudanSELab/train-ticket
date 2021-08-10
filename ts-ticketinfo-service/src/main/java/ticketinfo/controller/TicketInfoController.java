package ticketinfo.controller;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ticketinfo.entity.Travel;
import ticketinfo.service.TicketInfoService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/ticketinfoservice")
public class TicketInfoController {

    @Autowired
    TicketInfoService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketInfoController.class);

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ TicketInfo Service ] !";
    }

    @PostMapping(value = "/ticketinfo")
    public HttpEntity queryForTravel(@RequestBody Travel info, @RequestHeader HttpHeaders headers) {
        // TravelResult
        TicketInfoController.LOGGER.info("Query ticket info for travel,Start: {},End: {}",info.getStartingPlace(),info.getEndPlace());
        return ok(service.queryForTravel(info, headers));
    }

    @GetMapping(value = "/ticketinfo/{name}")
    public HttpEntity queryForStationId(@PathVariable String name, @RequestHeader HttpHeaders headers) {
        // String id
        TicketInfoController.LOGGER.info("Query ticket info for station id,Name: {}",name);
        return ok(service.queryForStationId(name, headers));
    }

}
