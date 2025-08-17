package config.controller;

import config.entity.Config;
import config.service.ConfigService;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/configservice/admin")
public class AdminConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConfigController.class);

    @Autowired
    private ConfigService configService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers){
        return "Welcome to [ Admin Config Service ] !";
    }

    @GetMapping("/configs")
    public ResponseEntity<Response> getAllConfigs(@RequestHeader HttpHeaders headers){
        LOGGER.info("[getAllConfigs][Admin get all configs]");
        return ok(configService.queryAll(headers));
    }

    @PostMapping("/configs")
    public ResponseEntity<Response> addConfig(@RequestBody Config config,@RequestHeader HttpHeaders headers){
        LOGGER.info("[addConfig][Admin add config][name: {}]",config.getName());
        return new ResponseEntity<>(configService.create(config, headers), HttpStatus.CREATED);
    }

    @PutMapping("/configs")
    public ResponseEntity<Response> modifyConfig(@RequestBody Config config,@RequestHeader HttpHeaders headers){
        LOGGER.info("[modifyConfig][Admin modify config][name: {}]",config.getName());
        return ok(configService.update(config, headers));
    }

    @DeleteMapping("/configs/{name}")
    public ResponseEntity<Response> deleteConfig(@PathVariable String name,@RequestHeader HttpHeaders headers){
        LOGGER.info("[deleteConfig][Admin delete config][name: {}]",name);
        return ok(configService.delete(name, headers));
    }
}
