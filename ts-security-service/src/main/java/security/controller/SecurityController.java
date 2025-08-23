package security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import security.service.SecurityService;
import edu.fudan.common.util.Response;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @GetMapping(value = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "welcome to [Security Service]";
    }

    @GetMapping(path = "/securityConfigs/{accountId}")
    public HttpEntity<Response<String>> check(@PathVariable String accountId, @RequestHeader HttpHeaders headers) {
        log.info("[check][Check Security][Check Account Id: {}]", accountId);
        return ok(securityService.check(accountId, headers));
    }
}
