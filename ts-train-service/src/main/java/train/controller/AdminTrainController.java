package train.controller;

import train.entity.TrainType;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import train.service.TrainService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/train/admin")
public class AdminTrainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTrainController.class);

    @Autowired
    private TrainService trainService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers){
        return "Welcome to [ AdminTrain Service ] !";
    }

    @GetMapping("/trains")
    public ResponseEntity<Response> getAllTrains(@RequestHeader HttpHeaders headers){
        LOGGER.info("[getAllTrains][Admin get all trains]");
        return ok(new Response(1, "success", trainService.query(headers)));
    }

    @PostMapping("/trains")
    public ResponseEntity<Response> addTrain(@RequestBody TrainType trainType,@RequestHeader HttpHeaders headers){
        LOGGER.info("[addTrain][Admin add train][id: {}]",trainType.getId());
        boolean res = trainService.create(trainType, headers);
        return ok(new Response(res?1:0, res?"success":"exists", null));
    }

    @PutMapping("/trains")
    public ResponseEntity<Response> modifyTrain(@RequestBody TrainType trainType,@RequestHeader HttpHeaders headers){
        LOGGER.info("[modifyTrain][Admin modify train][id: {}]",trainType.getId());
        boolean res = trainService.update(trainType, headers);
        return ok(new Response(res?1:0, res?"success":"not found", null));
    }

    @DeleteMapping("/trains/{trainId}")
    public ResponseEntity<Response> deleteTrain(@PathVariable String trainId,@RequestHeader HttpHeaders headers){
        LOGGER.info("[deleteTrain][Admin delete train][id: {}]",trainId);
        boolean res = trainService.delete(trainId, headers);
        return ok(new Response(res?1:0, res?"success":"not found", null));
    }
}
