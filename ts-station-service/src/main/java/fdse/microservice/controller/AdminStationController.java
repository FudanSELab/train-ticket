package fdse.microservice.controller;

import edu.fudan.common.client.dto.station.StationDto;
import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import fdse.microservice.mapper.StationMapper;
import fdse.microservice.service.StationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/station/admin")
@RequiredArgsConstructor
public class AdminStationController {

    private final StationService stationService;

    private final StationMapper stationMapper;

    private ResponseEntity<Response<StationDto>> respond(Response<Station> response) {
        return ok(stationMapper.toDtoResponse(response));
    }

    @PostMapping("/stations")
    public ResponseEntity<Response<StationDto>> addStation(@RequestBody StationDto stationDto,
            @RequestHeader HttpHeaders headers) {
        log.info("[addStation][Admin add station][station: {}]", stationDto);
        if (stationDto.getName().isEmpty()) {
            return new ResponseEntity<>(new Response<>(0, "Name not specify", null), HttpStatus.BAD_REQUEST);
        }
        return respond(stationService.create(stationMapper.toEntity(stationDto), headers));
    }

    @PutMapping("/stations")
    public ResponseEntity<Response<StationDto>> modifyStation(@RequestBody StationDto stationDto,
            @RequestHeader HttpHeaders headers) {
        log.info("[modifyStation][Admin modify station][id: {}]", stationDto.getId());
        if (stationDto.getId().isEmpty()) {
            return new ResponseEntity<>(new Response<>(0, "Id not specify", null), HttpStatus.BAD_REQUEST);
        }
        return respond(stationService.update(stationMapper.toEntity(stationDto), headers));
    }

    @DeleteMapping("/stations/{stationId}")
    public ResponseEntity<Response<StationDto>> deleteStation(@PathVariable String stationId,
            @RequestHeader HttpHeaders headers) {
        log.info("[deleteStation][Admin delete station][id: {}]", stationId);
        return respond(stationService.delete(stationId, headers));
    }
}
