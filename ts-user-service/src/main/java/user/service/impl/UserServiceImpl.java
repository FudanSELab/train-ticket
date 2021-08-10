package user.service.impl;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import user.dto.AuthDto;
import user.dto.UserDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.UserService;


import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_SERVICE_URI = "http://ts-auth-service:12340/api/v1";

    @Override
    public Response saveUser(UserDto userDto, HttpHeaders headers) {
        LOGGER.info("Save User Name id：" + userDto.getUserName());
        UUID userId = userDto.getUserId();
        if (userDto.getUserId() == null) {
            userId = UUID.randomUUID();
        }

        User user = User.builder()
                .userId(userId)
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .documentType(userDto.getDocumentType())
                .documentNum(userDto.getDocumentNum())
                .email(userDto.getEmail()).build();

        // avoid same user name
        User user1 = userRepository.findByUserName(userDto.getUserName());
        if (user1 == null) {

            createDefaultAuthUser(AuthDto.builder().userId(userId + "")
                    .userName(user.getUserName())
                    .password(user.getPassword()).build());

            User userSaveResult = userRepository.save(user);
            LOGGER.info("Send authorization message to ts-auth-service....");

            return new Response<>(1, "REGISTER USER SUCCESS", userSaveResult);
        } else {
            UserServiceImpl.LOGGER.error("Save user error.User already exists,UserId: {}",userDto.getUserId());
            return new Response<>(0, "USER HAS ALREADY EXISTS", null);
        }
    }

    private Response createDefaultAuthUser(AuthDto dto) {
        LOGGER.info("CALL TO AUTH");
        LOGGER.info("AuthDto : " + dto.toString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuthDto> entity = new HttpEntity<>(dto, null);
        ResponseEntity<Response<AuthDto>> res  = restTemplate.exchange("http://ts-auth-service:12340/api/v1/auth",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Response<AuthDto>>() {
                });
        return res.getBody();
    }

    @Override
    public Response getAllUsers(HttpHeaders headers) {
        List<User> users = userRepository.findAll();
        if (users != null && !users.isEmpty()) {
            return new Response<>(1, "Success", users);
        }
        UserServiceImpl.LOGGER.warn("Get all users warn: {}","No Content");
        return new Response<>(0, "NO User", null);
    }

    @Override
    public Response findByUserName(String userName, HttpHeaders headers) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return new Response<>(1, "Find User Success", user);
        }
        UserServiceImpl.LOGGER.warn("Get user by name warn,User Name: {}",userName);
        return new Response<>(0, "No User", null);
    }

    @Override
    public Response findByUserId(String userId, HttpHeaders headers) {
        User user = userRepository.findByUserId(UUID.fromString(userId));
        if (user != null) {
            return new Response<>(1, "Find User Success", user);
        }
        UserServiceImpl.LOGGER.error("Get user by id error,UserId: {}",userId);
        return new Response<>(0, "No User", null);
    }

    @Override
    public Response deleteUser(UUID userId, HttpHeaders headers) {
        LOGGER.info("DELETE USER BY ID :" + userId);
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            // first  only admin token can delete success
            deleteUserAuth(userId, headers);
            // second
            userRepository.deleteByUserId(userId);
            LOGGER.info("DELETE SUCCESS");
            return new Response<>(1, "DELETE SUCCESS", null);
        } else {
            UserServiceImpl.LOGGER.error("Delete user error.User not found,UserId: {}",userId);
            return new Response<>(0, "USER NOT EXISTS", null);
        }
    }

    @Override
    public Response updateUser(UserDto userDto, HttpHeaders headers) {
        LOGGER.info("UPDATE USER :" + userDto.toString());
        User oldUser = userRepository.findByUserName(userDto.getUserName());
        if (oldUser != null) {
            User newUser = User.builder().email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .userId(oldUser.getUserId())
                    .userName(userDto.getUserName())
                    .gender(userDto.getGender())
                    .documentNum(userDto.getDocumentNum())
                    .documentType(userDto.getDocumentType()).build();
            userRepository.deleteByUserId(oldUser.getUserId());
            userRepository.save(newUser);
            return new Response<>(1, "SAVE USER SUCCESS", newUser);
        } else {
            UserServiceImpl.LOGGER.error("Update user error.User not found,UserId: {}",userDto.getUserId());
            return new Response(0, "USER NOT EXISTS", null);
        }
    }

    public void deleteUserAuth(UUID userId, HttpHeaders headers) {
        LOGGER.info("DELETE USER BY ID :" + userId);

        HttpEntity<Response> httpEntity = new HttpEntity<>(null);
        restTemplate.exchange(AUTH_SERVICE_URI + "/users/" + userId,
                HttpMethod.DELETE,
                httpEntity,
                Response.class);
        LOGGER.info("DELETE USER AUTH SUCCESS");
    }
}
