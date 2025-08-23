package security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.service.SecurityService;
import edu.fudan.common.util.Response;

import static org.springframework.http.ResponseEntity.ok;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RestController
@RequestMapping("/api/v1/security/admin")
@Validated
public class SecurityAdminController {
    @Autowired
    private SecurityService securityService;

    /**
     * Update configuration max order one hour config value.
     */
    @PutMapping("/config/maxOrderOneHour/{value}")
    public ResponseEntity<Response<Boolean>> updateMaxOrderOneHour(@PathVariable @Positive Integer value, @RequestHeader HttpHeaders headers) {
        log.info("[updateMaxOrderOneHour][value:{}]", value);
        return ok(securityService.updateMaxOrderOneHour(value, headers));
    }

    /**
     * Update configuration max order not use config value.
     */
    @PutMapping("/config/maxOrderNotUse/{value}")
    public ResponseEntity<Response<Boolean>> updateMaxOrderNotUse(@PathVariable @Positive Integer value, @RequestHeader HttpHeaders headers) {
        log.info("[updateMaxOrderNotUse][value:{}]", value);
        return ok(securityService.updateMaxOrderNotUse(value, headers));
    }
}
