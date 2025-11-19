package train.controller;

import edu.fudan.common.client.dto.train.TrainTypeDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import train.entity.TrainType;
import train.mapper.TrainTypeMapper;
import train.service.TrainService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainTypeMapper trainTypeMapper;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Train Service ] !";
    }

    @GetMapping(value = "/trainTypes")
    public HttpEntity<Response<List<TrainTypeDto>>> query(@RequestHeader HttpHeaders headers) {
        log.info("[query][Query train]");
        return ok(toListResponse(trainService.query(headers), "no content"));
    }

    @GetMapping(value = "/trainTypes/{id}")
    public HttpEntity<Response<TrainTypeDto>> retrieve(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        log.info("[retrieve][Retrieve train][TrainTypeId: {}]", id);
        String msg = "here is no TrainType with the trainType id: " + id;
        return ok(toSingleResponse(trainService.retrieve(id, headers), msg));
    }

    @GetMapping(value = "/trainTypes/byName/{name}")
    public HttpEntity<Response<TrainTypeDto>> retrieveByName(@PathVariable String name, @RequestHeader HttpHeaders headers) {
        log.info("[retrieveByName][Retrieve train][TrainTypeName: {}]", name);
        String msg = "here is no TrainType with the trainType name: " + name;
        return ok(toSingleResponse(trainService.retrieveByName(name, headers), msg));
    }

    @PostMapping(value = "/trains/byNames")
    public HttpEntity<Response<List<TrainTypeDto>>> retrieveByName(@RequestBody List<String> names, @RequestHeader HttpHeaders headers) {
        log.info("[retrieveByNames][Retrieve train][TrainTypeNames: {}]", names);
        String msg = "here is no TrainTypes with the trainType names: " + names;
        return ok(toListResponse(trainService.retrieveByNames(names, headers), msg));
    }

    private Response<List<TrainTypeDto>> toListResponse(List<TrainType> entities, String emptyMsg) {
        List<TrainTypeDto> payload = entities == null ? null : trainTypeMapper.toDtoList(entities);
        boolean hasData = payload != null && !payload.isEmpty();
        return new Response<>(hasData ? 1 : 0, hasData ? "success" : emptyMsg, payload);
    }

    private Response<TrainTypeDto> toSingleResponse(TrainType entity, String notFoundMsg) {
        TrainTypeDto payload = entity == null ? null : trainTypeMapper.toDto(entity);
        boolean hasData = payload != null;
        return new Response<>(hasData ? 1 : 0, hasData ? "success" : notFoundMsg, payload);
    }
}
