package travel2.controller;

import edu.fudan.common.entity.TripResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel2.service.TravelService;

import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/travel2service")
public class Travel2Controller {

    @Autowired
    private TravelService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(Travel2Controller.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Travle2 Service ] !";
    }

    @GetMapping(value = "/train_types/{tripId}")
    public HttpEntity getTrainTypeByTripId(@PathVariable String tripId,
                                           @RequestHeader HttpHeaders headers) {
        // TrainType
        Travel2Controller.LOGGER.info("[getTrainTypeByTripId][Get train by Trip id][TripId: {}]",tripId);
        return ok(service.getTrainTypeByTripId(tripId, headers));
    }

    @GetMapping(value = "/routes/{tripId}")
    public HttpEntity getRouteByTripId(@PathVariable String tripId,
                                       @RequestHeader HttpHeaders headers) {
        Travel2Controller.LOGGER.info("[getRouteByTripId][Get Route By Trip ID][TripId: {}]", tripId);
        //Route
        return ok(service.getRouteByTripId(tripId, headers));
    }

    @PostMapping(value = "/trips/routes")
    public HttpEntity getTripsByRouteId(@RequestBody ArrayList<String> routeIds,
                                        @RequestHeader HttpHeaders headers) {
        // ArrayList<ArrayList<Trip>>
        Travel2Controller.LOGGER.info("[getTripByRoute][Get trips by Route id][RouteIdNumber: {}]",routeIds.size());
        return ok(service.getTripByRoute(routeIds, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips")
    public HttpEntity<?> createTrip(@RequestBody edu.fudan.common.entity.TravelInfo routeIds, @RequestHeader HttpHeaders headers) {
        // null
        Travel2Controller.LOGGER.info("[create][Create trip][TripId: {}]", routeIds.getTripId());
        return new ResponseEntity<>(service.create(routeIds, headers), HttpStatus.CREATED);
    }

    /**
     *Return Trip only, no left ticket information
     *
     * @param tripId trip id
     * @param headers headers
     * @return HttpEntity
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips/{tripId}")
    public HttpEntity retrieve(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // Trip
        Travel2Controller.LOGGER.info("[retrieve][Retrieve trip][TripId: {}]",tripId);
        return ok(service.retrieve(tripId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/trips")
    public HttpEntity updateTrip(@RequestBody edu.fudan.common.entity.TravelInfo info, @RequestHeader HttpHeaders headers) {
        // Trip
        Travel2Controller.LOGGER.info("[update][Update trip][TripId: {}]",info.getTripId());
        return ok(service.update(info, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/trips/{tripId}")
    public HttpEntity deleteTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // string
        Travel2Controller.LOGGER.info("[delete][Delete trip][TripId: {}]",tripId);
        return ok(service.delete(tripId, headers));
    }

    /**
     * Return Trip and the remaining tickets
     *
     * @param info trip info
     * @param headers headers
     * @return HttpEntity
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips/left")
    public HttpEntity queryInfo(@RequestBody edu.fudan.common.entity.TripInfo info, @RequestHeader HttpHeaders headers) {
        if (info.getStartPlace() == null || info.getStartPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null) {
            Travel2Controller.LOGGER.info("[query][Travel Query Fail][Something null]");
            ArrayList<TripResponse> errorList = new ArrayList<>();
            return ok(errorList);
        }
        Travel2Controller.LOGGER.info("[query][Query TripResponse]");
        return ok(service.queryByBatch(info, headers));
    }

    /**
     * Return a Trip and the remaining tickets
     *
     * @param gtdi trip all datail info
     * @param headers headers
     * @return HttpEntity
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trip_detail")
    public HttpEntity getTripAllDetailInfo(@RequestBody edu.fudan.common.entity.TripAllDetailInfo gtdi, @RequestHeader HttpHeaders headers) {
        Travel2Controller.LOGGER.info("[getTripAllDetailInfo][Get trip detail][TripId: {}]",gtdi.getTripId());
        return ok(service.getTripAllDetailInfo(gtdi, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        // List<Trip>
        Travel2Controller.LOGGER.info("[queryAll][Query all trips]");
        return ok(service.queryAll(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/admin_trip")
    public HttpEntity adminQueryAll(@RequestHeader HttpHeaders headers) {
        // ArrayList<AdminTrip>
        Travel2Controller.LOGGER.info("[adminQueryAll][Admin query all trips]");
        return ok(service.adminQueryAll(headers));
    }

}
