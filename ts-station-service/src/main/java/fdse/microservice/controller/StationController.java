package fdse.microservice.controller;

import edu.fudan.common.client.dto.station.StationDto;
import edu.fudan.common.util.Response;
import fdse.microservice.mapper.StationMapper;
import fdse.microservice.service.StationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/station")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

		private final StationMapper stationMapper;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Station Service ] !";
    }

    @GetMapping(value = "/stations")
    public ResponseEntity<Response<List<StationDto>>> query(@RequestHeader HttpHeaders headers) {
        return ok(stationMapper.toDtoListResponse(stationService.query(headers)));
    }

    // according to station name ---> query station id
    @GetMapping(value = "/stations/id/{stationNameForId}")
    public ResponseEntity<Response<String>> queryForStationId(@PathVariable(value = "stationNameForId")
                                                String stationName, @RequestHeader HttpHeaders headers) {
        // string
        log.info("[queryForId][Query for station id][StationName: {}]",stationName);
        return ok(stationService.queryForId(stationName, headers));
    }

    // according to station name list --->  query all station ids
    @PostMapping(value = "/stations/idlist")
    public ResponseEntity<Response<Map<String, String>>> queryForIdBatch(@RequestBody List<String> stationNameList, @RequestHeader HttpHeaders headers) {
        log.info("[queryForIdBatch][Query stations for id batch][StationNameNumbers: {}]",stationNameList.size());
        return ok(stationService.queryForIdBatch(stationNameList, headers));
    }

    // according to station id ---> query station name
    @GetMapping(value = "/stations/name/{stationIdForName}")
    public ResponseEntity<Response<String>> queryById(@PathVariable(value = "stationIdForName")
                                        String stationId, @RequestHeader HttpHeaders headers) {
        log.info("[queryById][Query stations By Id][Id: {}]", stationId);
        // string
        return ok(stationService.queryById(stationId, headers));
    }

    // according to station id list  ---> query all station names
    @PostMapping(value = "/stations/namelist")
    public ResponseEntity<Response<List<String>>> queryForNameBatch(@RequestBody List<String> stationIdList, @RequestHeader HttpHeaders headers) {
        log.info("[queryByIdBatch][Query stations for name batch][StationIdNumbers: {}]",stationIdList.size());
        return ok(stationService.queryByIdBatch(stationIdList, headers));
    }

}
