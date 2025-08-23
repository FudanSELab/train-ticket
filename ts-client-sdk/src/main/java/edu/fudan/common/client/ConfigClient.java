package edu.fudan.common.client;

import edu.fudan.common.client.dto.ConfigDto;
import edu.fudan.common.util.Response;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client for interacting with the Config Service.
 */
@Slf4j
@Component
public class ConfigClient {

    private static final String SERVICE_NAME = "ts-config-service";
    private static final String BASE_URL = "/api/v1/configservice";
    private static final String ADMIN_BASE_URL = "/api/v1/config/admin";

    @Autowired
    private RestTemplate restTemplate;

    private String getServiceUrl() {
        return "http://" + SERVICE_NAME;
    }

    /* ==================== Public Endpoints ==================== */

    /**
     * Get all configuration items.
     */
    public Response<List<ConfigDto>> getAllConfigs(HttpHeaders headers) {
        log.info("[getAllConfigs][Querying all configs]");
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Response<List<ConfigDto>>> response = restTemplate.exchange(
            getServiceUrl() + BASE_URL + "/configs",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Response<List<ConfigDto>>>() {}
        );
        return response.getBody();
    }

    /**
     * Retrieve a single configuration by name.
     */
    public Response<ConfigDto> getConfigByName(String name, HttpHeaders headers) {
        log.info("[getConfigByName][Retrieving config][name: {}]", name);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Response<ConfigDto>> response = restTemplate.exchange(
            getServiceUrl() + BASE_URL + "/configs/" + name,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Response<ConfigDto>>() {}
        );
        return response.getBody();
    }

    /* ==================== Admin Endpoints ==================== */

    /**
     * Add a new configuration (Admin).
     */
    public Response<ConfigDto> addConfig(ConfigDto config, HttpHeaders headers) {
        log.info("[addConfig][Admin add config][name: {}]", config.getName());
        HttpEntity<ConfigDto> entity = new HttpEntity<>(config, headers);
        ResponseEntity<Response<ConfigDto>> response = restTemplate.exchange(
            getServiceUrl() + ADMIN_BASE_URL + "/configs",
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<Response<ConfigDto>>() {}
        );
        return response.getBody();
    }

    /**
     * Modify an existing configuration (Admin).
     */
    public Response<ConfigDto> updateConfig(ConfigDto config, HttpHeaders headers) {
        log.info("[updateConfig][Admin update config][name: {}]", config.getName());
        HttpEntity<ConfigDto> entity = new HttpEntity<>(config, headers);
        ResponseEntity<Response<ConfigDto>> response = restTemplate.exchange(
            getServiceUrl() + ADMIN_BASE_URL + "/configs",
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<Response<ConfigDto>>() {}
        );
        return response.getBody();
    }

    /**
     * Delete a configuration by name (Admin).
     */
    public Response<ConfigDto> deleteConfig(String name, HttpHeaders headers) {
        log.info("[deleteConfig][Admin delete config][name: {}]", name);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Response<ConfigDto>> response = restTemplate.exchange(
            getServiceUrl() + ADMIN_BASE_URL + "/configs/" + name,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<Response<ConfigDto>>() {}
        );
        return response.getBody();
    }
}
