package config.service;

import config.entity.Config;
import config.repository.ConfigRepository;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author fdse
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    String config0 = "Config ";

    @Override
    public Response create(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) != null) {
            String result = config0 + info.getName() + " already exists.";
            logger.warn(result);
            return new Response<>(0, result, null);
        } else {
            Config config = new Config(info.getName(), info.getValue(), info.getDescription());
            repository.save(config);
            logger.info("Config: ({}) create success", info);
            return new Response<>(1, "Create success", config);
        }
    }

    @Override
    public Response update(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) == null) {
            String result = config0 + info.getName() + " doesn't exist.";
            logger.warn(result);
            return new Response<>(0, result, null);
        } else {
            Config config = new Config(info.getName(), info.getValue(), info.getDescription());
            repository.save(config);
            logger.info("Config: ({}) update success", config);
            return new Response<>(1, "Update success", config);
        }
    }


    @Override
    public Response query(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            logger.warn("Config does not exist, name: {}, message: {}", name, "No content");
            return new Response<>(0, "No content", null);
        } else {
            logger.info("Query config {} success", name);
            return new Response<>(1, "Success", config);
        }
    }

    @Override
    public Response delete(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            String result = config0 + name + " doesn't exist.";
            logger.warn(result);
            return new Response<>(0, result, null);
        } else {
            repository.deleteByName(name);
            logger.info("Config {} delete success", name);
            return new Response<>(1, "Delete success", config);
        }
    }

    @Override
    public Response queryAll(HttpHeaders headers) {
        List<Config> configList = repository.findAll();

        if (configList != null && !configList.isEmpty()) {
            logger.info("Query all config success");
            return new Response<>(1, "Find all  config success", configList);
        } else {
            logger.warn("Query config: {}", "No content");
            return new Response<>(0, "No content", null);
        }
    }
}
