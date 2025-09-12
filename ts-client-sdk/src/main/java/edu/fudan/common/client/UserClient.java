package edu.fudan.common.client;

import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Client for interacting with ts-user-service user and admin endpoints.
 */
@Slf4j
@Component
public class UserClient {

    private static final String SERVICE_NAME = "ts-user-service";
    private static final String BASE_USER = "/api/v1/user";
    private static final String BASE_ADMIN = "/api/v1/user/admin/users";

    @Autowired
    private RestTemplate restTemplate;

    private String getServiceUrl() {
        return "http://" + SERVICE_NAME;
    }

    /* ---------- Common helpers ---------- */
    private <T> Response<T> exchange(String url, HttpMethod method, Object body, HttpHeaders headers, ParameterizedTypeReference<Response<T>> type) {
        HttpEntity<?> entity = body == null ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);
        ResponseEntity<Response<T>> resp = restTemplate.exchange(url, method, entity, type);
        return resp.getBody();
    }

    /* ---------- User endpoints ---------- */
    public Response<UserDto> register(UserDto dto, HttpHeaders headers) {
        log.info("[UserClient][register][username: {}]", dto.getUserName());
        return exchange(getServiceUrl() + BASE_USER + "/register", HttpMethod.POST, dto, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    public Response<UserDto> getMe(HttpHeaders headers, HttpServletRequest request) {
        // Simply call /me; ts-user-service will verify token itself.
        return exchange(getServiceUrl() + BASE_USER + "/me", HttpMethod.GET, null, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    public Response<UserDto> updateMe(UserDto dto, HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_USER + "/me", HttpMethod.PUT, dto, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    /* ---------- Admin endpoints ---------- */
    public String adminWelcome(HttpHeaders headers) {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resp = restTemplate.exchange(getServiceUrl() + BASE_ADMIN + "/welcome", HttpMethod.GET, entity, String.class);
        return resp.getBody();
    }

    public Response<UserDto> adminGetById(String userId, HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_ADMIN + "/id/" + userId, HttpMethod.GET, null, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    public Response<List<UserDto>> adminGetAll(HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_ADMIN, HttpMethod.GET, null, headers,
                new ParameterizedTypeReference<Response<List<UserDto>>>() {});
    }

    public Response<UserDto> adminAddUser(UserDto dto, HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_ADMIN, HttpMethod.POST, dto, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    public Response<UserDto> adminUpdateUser(UserDto dto, HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_ADMIN, HttpMethod.PUT, dto, headers,
                new ParameterizedTypeReference<Response<UserDto>>() {});
    }

    public Response<Void> adminDeleteUser(String userId, HttpHeaders headers) {
        return exchange(getServiceUrl() + BASE_ADMIN + "/" + userId, HttpMethod.DELETE, null, headers,
                new ParameterizedTypeReference<Response<Void>>() {});
    }
}
