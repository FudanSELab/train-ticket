package adminuser.service;

import adminuser.dto.UserDto;
import adminuser.entity.*;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

/**
 * @author fdse
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);
    private static final String USER_SERVICE_IP_URI = "http://ts-user-service:12342/api/v1/userservice/users";


    @Override
    public Response getAllUsers(HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response<List<User>>> re = restTemplate.exchange(
                USER_SERVICE_IP_URI,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<List<User>>>() {
                });
        if (re.getBody() == null || re.getBody().getStatus() != 1) {
            AdminUserServiceImpl.LOGGER.error("Get All Users error");
            return new Response<>(0, "get all users error", null);
        }
        AdminUserServiceImpl.LOGGER.info("Get All Users");
        return re.getBody();
    }


    @Override
    public Response deleteUser(String userId, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                USER_SERVICE_IP_URI + "/" + userId,
                HttpMethod.DELETE,
                requestEntity,
                Response.class);
        if (re.getBody() == null || re.getBody().getStatus() != 1) {
            AdminUserServiceImpl.LOGGER.error("Delete user error, userId: {}", userId);
            return new Response<>(0, "delete user error", null);
        }
        AdminUserServiceImpl.LOGGER.info("Delete user success, userId: {}", userId);
        return re.getBody();
    }

    @Override
    public Response updateUser(UserDto userDto, HttpHeaders headers) {
        LOGGER.info("UPDATE USER: " + userDto.toString());
        HttpEntity requestEntity = new HttpEntity(userDto, null);
        ResponseEntity<Response> re = restTemplate.exchange(
                USER_SERVICE_IP_URI,
                HttpMethod.PUT,
                requestEntity,
                Response.class);

        String userName = userDto.getUserName();
        if (re.getBody() == null || re.getBody().getStatus() != 1) {
            AdminUserServiceImpl.LOGGER.error("Update user error, userName: {}", userName);
            return new Response<>(0, "Update user error", null);
        }
        AdminUserServiceImpl.LOGGER.info("Update user success, userName: {}", userName);
        return re.getBody();
    }

    @Override
    public Response addUser(UserDto userDto, HttpHeaders headers) {
        LOGGER.info("ADD USER INFO : "+userDto.toString());
        HttpEntity requestEntity = new HttpEntity(userDto, null);
        ResponseEntity<Response<User>> re = restTemplate.exchange(
                USER_SERVICE_IP_URI + "/register",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<User>>() {
                });

        String userName = userDto.getUserName();
        if (re.getBody() == null || re.getBody().getStatus() != 1) {
            AdminUserServiceImpl.LOGGER.error("Add user error, userName: {}", userName);
            return new Response<>(0, "Add user error", null);
        }
        AdminUserServiceImpl.LOGGER.info("Add user success, userName: {}", userName);
        return re.getBody();
    }
}
