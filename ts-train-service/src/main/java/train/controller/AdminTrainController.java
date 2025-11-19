package train.controller;

import edu.fudan.common.client.dto.train.TrainTypeDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import train.entity.TrainType;
import train.mapper.TrainTypeMapper;
import train.service.TrainService;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/train/admin")
public class AdminTrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainTypeMapper trainTypeMapper;

    @PostMapping("/trainTypes")
    public ResponseEntity<Response<Void>> addTrain(@RequestBody TrainTypeDto trainTypeDto,
                                                   @RequestHeader HttpHeaders headers){
        log.info("[addTrain][Admin add train][name: {}]", trainTypeDto.getName());
        boolean created = trainService.create(toEntity(trainTypeDto), headers);
        return ok(buildVoidResponse(created, "exists"));
    }

    @PutMapping("/trainTypes")
    public ResponseEntity<Response<Void>> modifyTrain(@RequestBody TrainTypeDto trainTypeDto,
                                                      @RequestHeader HttpHeaders headers){
        log.info("[modifyTrain][Admin modify train][id: {}]", trainTypeDto.getId());
        boolean updated = trainService.update(toEntity(trainTypeDto), headers);
        return ok(buildVoidResponse(updated, "not found"));
    }

    @DeleteMapping("/trainTypes/{trainTypeId}")
    public ResponseEntity<Response<Void>> deleteTrain(@PathVariable("trainTypeId") String trainTypeId,
                                                      @RequestHeader HttpHeaders headers){
        log.info("[deleteTrain][Admin delete train][id: {}]", trainTypeId);
        boolean deleted = trainService.delete(trainTypeId, headers);
        return ok(buildVoidResponse(deleted, "not found"));
    }

    private TrainType toEntity(TrainTypeDto dto) {
        return dto == null ? null : trainTypeMapper.toEntity(dto);
    }

    private Response<Void> buildVoidResponse(boolean success, String failureMsg) {
        return new Response<>(success ? 1 : 0, success ? "success" : failureMsg, null);
    }
}
