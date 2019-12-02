package fdse.microservice.controller;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.*;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/stationservice")
public class StationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private StationService stationService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Station Service ] !";
    }

    @GetMapping(value = "/stations")
    public HttpEntity query(@RequestHeader HttpHeaders headers) {
        return ok(stationService.query(headers));
    }

    @PostMapping(value = "/stations")
    public ResponseEntity<Response> create(@RequestBody StationDTO stationDTO, @RequestHeader HttpHeaders headers) {
        Station station = new Station(stationDTO.id, stationDTO.name, stationDTO.stayTime);
        return new ResponseEntity<>(stationService.create(station, headers), HttpStatus.CREATED);
    }

    @PutMapping(value = "/stations")
    public HttpEntity update(@RequestBody StationDTO stationDTO, @RequestHeader HttpHeaders headers) {
        Station station = new Station(stationDTO.id, stationDTO.name, stationDTO.stayTime);
        return ok(stationService.update(station, headers));
    }

    @DeleteMapping(value = "/stations")
    public ResponseEntity<Response> delete(@RequestBody StationDTO stationDTO, @RequestHeader HttpHeaders headers) {
        Station station = new Station(stationDTO.id, stationDTO.name, stationDTO.stayTime);
        return ok(stationService.delete(station, headers));
    }




    // 根据车站名 ---> 查询 车站 id
    @GetMapping(value = "/stations/id/{stationNameForId}")
    public HttpEntity queryForStationId(@PathVariable(value = "stationNameForId")
                                                String stationName, @RequestHeader HttpHeaders headers) {
        // string
        return ok(stationService.queryForId(stationName, headers));
    }

    // 根据车站名 list --->  查询 所有车站 id
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/idlist")
    public HttpEntity queryForIdBatch(@RequestBody List<String> stationNameList, @RequestHeader HttpHeaders headers) {
        return ok(stationService.queryForIdBatch(stationNameList, headers));
    }

    // 根据station id 查询  车站名
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/stations/name/{stationIdForName}")
    public HttpEntity queryById(@PathVariable(value = "stationIdForName")
                                        String stationId, @RequestHeader HttpHeaders headers) {
        logger.info("[Station Service] Query By Id:{}",stationId);
        // string
        return ok(stationService.queryById(stationId, headers));
    }

    // 根据 station  id list  ---> 查询 所有 车站名
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/stations/namelist")
    public HttpEntity queryForNameBatch(@RequestBody List<String> stationIdList, @RequestHeader HttpHeaders headers) {
        return ok(stationService.queryByIdBatch(stationIdList, headers));
    }


}
