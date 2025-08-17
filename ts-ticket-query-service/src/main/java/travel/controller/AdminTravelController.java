package travel.controller;

import edu.fudan.common.entity.TravelInfo;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.service.AdminTravelService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/ticketquery/admin")
public class AdminTravelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTravelController.class);

    @Autowired
    private AdminTravelService adminTravelService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminTravel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/admintravel")
    public ResponseEntity<Response> getAllTravels(@RequestHeader HttpHeaders headers) {
        LOGGER.info("[getAllTravels][Get all travels]");
        return ok(adminTravelService.getAllTravels(headers));
    }

    @PostMapping("/admintravel")
    public ResponseEntity<Response> addTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addTravel][Add travel][trip id: {}]", request.getTripId());
        return ok(adminTravelService.addTravel(request, headers));
    }

    @PutMapping("/admintravel")
    public ResponseEntity<Response> updateTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[updateTravel][Update travel][trip id: {}]", request.getTripId());
        return ok(adminTravelService.updateTravel(request, headers));
    }

    @DeleteMapping("/admintravel/{tripId}")
    public ResponseEntity<Response> deleteTravel(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[deleteTravel][Delete travel][trip id: {}]", tripId);
        return ok(adminTravelService.deleteTravel(tripId, headers));
    }
}
