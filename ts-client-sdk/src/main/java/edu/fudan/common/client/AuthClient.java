package edu.fudan.common.client;

import edu.fudan.common.util.Response;
import edu.fudan.common.client.dto.AuthDto;
import edu.fudan.common.client.dto.BasicAuthDto;
import edu.fudan.common.client.dto.TokenDto;
import edu.fudan.common.client.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.OutputStream;
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
     * Create a default user in the auth service
     *
     * @param userId User ID
     * @param userName Username
     * @param password Password
     * @return Response containing the created auth user
     */
    public Response<AuthDto> createDefaultUser(String userId, String userName, String password) {
        log.info("[createDefaultUser][Creating default auth user][UserId: {}, UserName: {}]", userId, userName);
        
        AuthDto authDto = new AuthDto(userId, userName, password);
        HttpEntity<AuthDto> entity = new HttpEntity<>(authDto);

        ResponseEntity<Response<AuthDto>> response = restTemplate.exchange(
            getServiceUrl() + BASE_URL,
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<Response<AuthDto>>() {}
        );

        return response.getBody();
    }

    /**
     * Login user and get authentication token
     *
     * @param userName Username
     * @param password Password
     * @param verificationCode Optional verification code
     * @param headers HTTP headers
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
            new ParameterizedTypeReference<Response<TokenDto>>() {}
        );

        return response.getBody();
    }

    /**
     * Get all users
     *
     * @param headers HTTP headers (for authentication)
     * @return List of users
     */
    public List<UserDto> getAllUsers(HttpHeaders headers) {
        log.info("[getAllUsers][Getting all users]");
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<UserDto>> response = restTemplate.exchange(
            getServiceUrl() + BASE_URL + "/users",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<UserDto>>() {}
        );

        return response.getBody();
    }

    /**
     * Delete user by ID
     *
     * @param userId User ID to delete
     * @param headers HTTP headers (for authentication)
     * @return Response indicating success or failure
     */
    public Response<Void> deleteUser(String userId, HttpHeaders headers) {
        log.info("[deleteUser][Deleting user][userId: {}]", userId);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Response<Void>> response = restTemplate.exchange(
            getServiceUrl() + BASE_URL + "/users/" + userId,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<Response<Void>>() {}
        );

        return response.getBody();
    }

    /**
     * Verify a verification code
     *
     * @param code The verification code to verify
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
            Boolean.class
        );

        return Boolean.TRUE.equals(response.getBody());
    }
}
