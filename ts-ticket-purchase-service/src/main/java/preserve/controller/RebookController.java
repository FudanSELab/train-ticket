package preserve.controller;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import preserve.entity.RebookInfo;
import preserve.service.RebookService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/ticket-purchase/rebook")
public class RebookController {

    @Autowired
    private RebookService rebookService;

    @PostMapping
    public ResponseEntity<Response> rebook(@RequestBody RebookInfo info, @RequestHeader HttpHeaders headers){
        return ok(rebookService.rebook(info, headers));
    }
}
