package fdse.microservice.controller;

import fdse.microservice.entity.Station;
import edu.fudan.common.util.Response;
import fdse.microservice.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/station/admin")
public class AdminStationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminStationController.class);

    @Autowired
    private StationService stationService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers){
        return "Welcome to [ AdminStation Service ] !";
    }

    @GetMapping("/stations")
    public ResponseEntity<Response> getAllStations(@RequestHeader HttpHeaders headers){
        LOGGER.info("[getAllStations][Admin get all stations]");
        return ok(stationService.query(headers));
    }

    @PostMapping("/stations")
    public ResponseEntity<Response> addStation(@RequestBody Station station,@RequestHeader HttpHeaders headers){
        LOGGER.info("[addStation][Admin add station][id: {}]",station.getId());
        return ok(stationService.create(station, headers));
    }

    @PutMapping("/stations")
    public ResponseEntity<Response> modifyStation(@RequestBody Station station,@RequestHeader HttpHeaders headers){
        LOGGER.info("[modifyStation][Admin modify station][id: {}]",station.getId());
        return ok(stationService.update(station, headers));
    }

    @DeleteMapping("/stations/{stationId}")
    public ResponseEntity<Response> deleteStation(@PathVariable String stationId,@RequestHeader HttpHeaders headers){
        LOGGER.info("[deleteStation][Admin delete station][id: {}]",stationId);
        return ok(stationService.delete(stationId, headers));
    }
}
