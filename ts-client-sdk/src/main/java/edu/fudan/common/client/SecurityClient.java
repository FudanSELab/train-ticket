package edu.fudan.common.client;

import edu.fudan.common.util.Response;
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
 * Client for interacting with ts-security-service.
 */
@Slf4j
@Component
public class SecurityClient {

	private static final String SERVICE_NAME = "ts-security-service";
	private static final String BASE_URL = "/api/v1/security";
	private static final String ADMIN_BASE_URL = "/api/v1/security/admin";

	@Autowired
	private RestTemplate restTemplate;

	private String getServiceUrl() {
		return "http://" + SERVICE_NAME;
	}

	/**
	 * Check order security for an account. Equivalent to SecurityController#check.
	 */
	public Response<String> check(String accountId, HttpHeaders headers) {
		log.info("[SecurityClient][check][accountId:{}]", accountId);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Response<String>> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/securityConfigs/" + accountId,
				HttpMethod.GET,
				entity,
				new ParameterizedTypeReference<Response<String>>() {
				});
		return response.getBody();
	}

	/**
	 * Update max order one hour configuration.
	 */
	public Response<Boolean> updateMaxOrderOneHour(Integer value, HttpHeaders headers) {
		log.info("[SecurityClient][updateMaxOrderOneHour][value:{}]", value);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Response<Boolean>> response = restTemplate.exchange(
				getServiceUrl() + ADMIN_BASE_URL + "/config/maxOrderOneHour/" + value,
				HttpMethod.PUT,
				entity,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		return response.getBody();
	}

	/**
	 * Update max order not use configuration.
	 */
	public Response<Boolean> updateMaxOrderNotUse(Integer value, HttpHeaders headers) {
		log.info("[SecurityClient][updateMaxOrderNotUse][value:{}]", value);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Response<Boolean>> response = restTemplate.exchange(
				getServiceUrl() + ADMIN_BASE_URL + "/config/maxOrderNotUse/" + value,
				HttpMethod.PUT,
				entity,
				new ParameterizedTypeReference<Response<Boolean>>() {
				});
		return response.getBody();
	}
}
