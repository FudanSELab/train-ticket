package config.controller;

import edu.fudan.common.client.dto.config.ConfigDto;
import config.service.ConfigService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import config.entity.Config;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/config/admin")
public class AdminConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private config.mapper.ConfigMapper configMapper;

    @PostMapping("/configs")
    public ResponseEntity<Response<ConfigDto>> addConfig(@RequestBody ConfigDto dto,@RequestHeader HttpHeaders headers){
        log.info("[addConfig][Admin add config][name: {}]",dto.getName());
        Config entity = configMapper.toEntity(dto);
        Response<Config> resp = configService.create(entity, headers);
        return new ResponseEntity<>(configMapper.toDtoResponse(resp), HttpStatus.CREATED);
    }

    @PutMapping("/configs")
    public ResponseEntity<Response<ConfigDto>> modifyConfig(@RequestBody ConfigDto dto,@RequestHeader HttpHeaders headers){
        log.info("[modifyConfig][Admin modify config][name: {}]",dto.getName());
        Response<Config> resp = configService.update(configMapper.toEntity(dto), headers);
        return ok(configMapper.toDtoResponse(resp));
    }

    @DeleteMapping("/configs/{name}")
    public ResponseEntity<Response<ConfigDto>> deleteConfig(@PathVariable String name,@RequestHeader HttpHeaders headers){
        log.info("[deleteConfig][Admin delete config][name: {}]",name);
        Response<Config> resp = configService.delete(name, headers);
        return ok(configMapper.toDtoResponse(resp));
    }
}
