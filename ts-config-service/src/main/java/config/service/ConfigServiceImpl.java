package config.service;

import config.entity.Config;
import config.repository.ConfigRepository;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ConfigRepository repository;

    @Override
    public Response<Config> create(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) != null) {
            String result = String.format("Config %s already exists.", info.getName());
            log.warn("[create][{} already exists][config info: {}]", result, info.getName());
            return new Response<>(0, result, null);
        }

        Config config = new Config(info.getName(), info.getValue(), info.getDescription());
        repository.save(config);
        log.info("[create][create success][Config: {}]", info);
        return new Response<>(1, "Create success", config);
    }

    @Override
    public Response<Config> update(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) == null) {
            log.warn(String.format("[update][%s doesn't exist][config info: {}]", info.getName()));
            return new Response<>(0, "Not found", null);
        }

        Config config = new Config(info.getName(), info.getValue(), info.getDescription());
        repository.save(config);
        log.info("[update][update success][Config: {}]", config);
        return new Response<>(1, "Update success", config);
    }

    @Override
    public Response<Config> query(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            log.warn("[query][Config does not exist][name: {}]", name);
            return new Response<>(0, "No content", null);
        }

        log.info("[query][Query config success][config name: {}]", name);
        return new Response<>(1, "Success", config);
    }

    @Override
    @Transactional
    public Response<Config> delete(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            log.warn("[delete][config doesn't exist][config name: {}]", name);
            return new Response<>(0, "No content", null);
        }

        repository.deleteByName(name);
        log.info("[delete][Config delete success][config name: {}]", name);
        return new Response<>(1, "Delete success", config);
    }

    @Override
    public Response<List<Config>> queryAll(HttpHeaders headers) {
        List<Config> configList = repository.findAll();
        log.info("[queryAll] configs len: {}", configList.size());
        return new Response<>(1, "Find all config success", configList);
    }
}
