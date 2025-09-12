package edu.fudan.common.client;

import edu.fudan.common.util.Response;
import edu.fudan.common.client.dto.auth.AuthDto;
import edu.fudan.common.client.dto.auth.BasicAuthDto;
import edu.fudan.common.client.dto.auth.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Client for interacting with the Auth Service
 */
@Slf4j
@Component
public class AuthClient {

	private static final String SERVICE_NAME = "ts-auth-service";
	private static final String BASE_URL = "/api/v1/auth";

	@Autowired
	private RestTemplate restTemplate;

	private String getServiceUrl() {
		return "http://" + SERVICE_NAME;
	}

	/**
	 * Admin: Delete user by ID
	 * <p>
	 * Calls the admin endpoint ("/admin/users/{userId}") exposed by the
	 * auth-service.
	 *
	 * @param userId  ID of the user to delete
	 * @param headers HTTP headers containing authentication / authorization info
	 * @return Response indicating success or failure
	 */
	public Response<String> adminDeleteUser(String userId, HttpHeaders headers) {
		log.info("[adminDeleteUser][Admin deleting user][userId: {}]", userId);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Response<String>> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/admin/users/" + userId,
				HttpMethod.DELETE,
				entity,
				new ParameterizedTypeReference<Response<String>>() {
				});

		return response.getBody();
	}

	/**
	 * Admin: Get all users
	 * <p>
	 * Calls the admin endpoint ("/admin/users") exposed by the auth-service.
	 *
	 * @param headers HTTP headers containing authentication / authorization info
	 * @return List of all users managed by auth-service
	 */
	public List<AuthDto> adminGetAllUsers(HttpHeaders headers) {
		log.info("[adminGetAllUsers][Admin request to get all users]");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<List<AuthDto>> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/admin/users",
				HttpMethod.GET,
				entity,
				new ParameterizedTypeReference<List<AuthDto>>() {
				});

		return response.getBody();
	}

	/**
	 * Create a default user in the auth service
	 *
	 * @param userId   User ID
	 * @param userName Username
	 * @param password Password
	 * @return Response containing the created auth user
	 */
	public Response<AuthDto> createDefaultUser(String userId, String userName, String password) {
		log.info("[createDefaultUser][Creating default auth user][UserId: {}, UserName: {}]", userId, userName);

		AuthDto authDto = AuthDto.builder().userId(userId).userName(userName).password(password).build();
		HttpEntity<AuthDto> entity = new HttpEntity<>(authDto);

		ResponseEntity<Response<AuthDto>> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/users",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<Response<AuthDto>>() {
				});

		return response.getBody();
	}

	/**
	 * Login user and get authentication token
	 *
	 * @param userName         Username
	 * @param password         Password
	 * @param verificationCode Optional verification code
	 * @param headers          HTTP headers
	 * @return Response containing the authentication token
	 */
	public Response<TokenDto> login(String userName, String password, String verificationCode, HttpHeaders headers) {
		log.info("[login][User login request][UserName: {}]", userName);

		BasicAuthDto authDto = new BasicAuthDto();
		authDto.setUsername(userName);
		authDto.setPassword(password);
		authDto.setVerificationCode(verificationCode);

		HttpEntity<BasicAuthDto> entity = new HttpEntity<>(authDto, headers);

		ResponseEntity<Response<TokenDto>> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/users/login",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<Response<TokenDto>>() {
				});

		return response.getBody();
	}

	/**
	 * Verify a verification code
	 *
	 * @param code    The verification code to verify
	 * @param headers HTTP headers
	 * @return true if the code is valid, false otherwise
	 */
	public boolean verifyCode(String code, HttpHeaders headers) {
		log.info("[verifyCode][Verifying code: {}]", code);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Boolean> response = restTemplate.exchange(
				getServiceUrl() + BASE_URL + "/verifycode/verify/" + code,
				HttpMethod.GET,
				entity,
				Boolean.class);

		return Boolean.TRUE.equals(response.getBody());
	}
}
