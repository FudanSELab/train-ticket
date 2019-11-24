package preserveother.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import preserveother.entity.OrderTicketsInfo;
import preserveother.service.PreserveOtherService;

import static org.springframework.http.ResponseEntity.ok;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fdu
 */
@RestController
@RequestMapping("/api/v1/preserveotherservice")
public class PreserveOtherController {
    private static Logger logger = Logger.getLogger(PreserveOtherController.class.getName());

    @Autowired
    private PreserveOtherService preserveService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ PreserveOther Service ] !";
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/preserveother")
    public HttpEntity preserve(@RequestBody OrderTicketsInfo oti,
                               @RequestHeader HttpHeaders headers) {

        logger.log(Level.INFO, () -> "[Preserve Other Service][Preserve] Account " + oti.getAccountId() + " order from " +
                oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());

        return ok(preserveService.preserve(oti, headers));
    }
}
