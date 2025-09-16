package config.controller;

import java.util.List;
import edu.fudan.common.client.dto.config.ConfigDto;
import config.service.ConfigService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author  Chenjie Xu
 * @date 2017/5/11.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private config.mapper.ConfigMapper configMapper;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Config Service ] !";
    }

    @GetMapping(value = "/configs")
    public HttpEntity<Response<List<ConfigDto>>> queryAll(@RequestHeader HttpHeaders headers) {
        log.info("[queryAll][Query all configs]");
        return ok(configMapper.toDtoListResponse(configService.queryAll(headers)));
    }

    @GetMapping(value = "/configs/{configName}")
    public HttpEntity<Response<ConfigDto>> retrieve(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        log.info("[retrieve][Retrieve config][configName: {}]", configName);
        return ok(configMapper.toDtoResponse(configService.query(configName, headers)));
    }
}
