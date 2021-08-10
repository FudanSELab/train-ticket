package config.controller;

import config.entity.Config;
import config.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.ResponseEntity.ok;

/**
 * @author  Chenjie Xu
 * @date 2017/5/11.
 */
@RestController
@RequestMapping("api/v1/configservice")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Config Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configs")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        logger.info("Query all config");
        return ok(configService.queryAll(headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/configs")
    public HttpEntity<?> createConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        logger.info("Create config");
        return new ResponseEntity<>(configService.create(info, headers), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/configs")
    public HttpEntity updateConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        logger.info("Update config");
        return ok(configService.update(info, headers));
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/configs/{configName}")
    public HttpEntity deleteConfig(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        logger.info("Delete config");
        return ok(configService.delete(configName, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configs/{configName}")
    public HttpEntity retrieve(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        logger.info("Retrieve config: {}", configName);
        return ok(configService.query(configName, headers));
    }



}
