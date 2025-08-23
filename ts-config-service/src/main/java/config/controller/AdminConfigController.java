package config.controller;

import config.entity.Config;
import config.service.ConfigService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/config/admin")
public class AdminConfigController {
    @Autowired
    private ConfigService configService;

    @PostMapping("/configs")
    public ResponseEntity<Response<Config>> addConfig(@RequestBody Config config,@RequestHeader HttpHeaders headers){
        log.info("[addConfig][Admin add config][name: {}]",config.getName());
        return new ResponseEntity<>(configService.create(config, headers), HttpStatus.CREATED);
    }

    @PutMapping("/configs")
    public ResponseEntity<Response<Config>> modifyConfig(@RequestBody Config config,@RequestHeader HttpHeaders headers){
        log.info("[modifyConfig][Admin modify config][name: {}]",config.getName());
        return ok(configService.update(config, headers));
    }

    @DeleteMapping("/configs/{name}")
    public ResponseEntity<Response<Config>> deleteConfig(@PathVariable String name,@RequestHeader HttpHeaders headers){
        log.info("[deleteConfig][Admin delete config][name: {}]",name);
        return ok(configService.delete(name, headers));
    }
}
