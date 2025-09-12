package user.service.impl;

import edu.fudan.common.util.Response;
import edu.fudan.common.client.AuthClient;
import edu.fudan.common.client.dto.auth.AuthDto;
import edu.fudan.common.client.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import user.entity.User;
import user.repository.UserRepository;
import user.service.UserService;


import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthClient authClient;

    @Override
    public Response<User> createUser(User user, String password, HttpHeaders headers) {
        log.info("[createUser][Save User Name][user name: {}]", user.getUserName());

        String userId = user.getUserId();
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            user.setUserId(userId);
        }

        // Check for duplicate username early
        if (userRepository.findByUserName(user.getUserName()) != null) {
            log.error("[createUser][User already exists][UserName: {}]", user.getUserName());
            return new Response<>(0, "USER HAS ALREADY EXISTS", null);
        }

        // Create default auth user first
        createDefaultAuthUser(AuthDto.builder()
                .userId(userId)
                .userName(user.getUserName())
                .password(user.getPassword())
                .build());

        User userSaveResult = userRepository.save(user);
        log.info("[createUser][User saved and auth created][userId: {}]", userId);
        return new Response<>(1, "REGISTER USER SUCCESS", userSaveResult);
    }

    private Response<AuthDto> createDefaultAuthUser(AuthDto dto) {
        log.info("[createDefaultAuthUser][CALL TO AUTH][AuthDto: {}]", dto.toString());
        return authClient.createDefaultUser(dto.getUserId(), dto.getUserName(), dto.getPassword());
    }

    @Override
    public Response<List<User>> getAllUsers(HttpHeaders headers) {
        List<User> users = userRepository.findAll();
        log.info("[getAllUsers] length of users: {}", users.size());
        return new Response<>(1, "Success", users);
    }

    @Override
    public Response<User> findByUserId(String userId, HttpHeaders headers) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            log.error("[findByUserId][User not found][UserId: {}]", userId);
            return new Response<>(0, "No User", null);
        }
        return new Response<>(1, "Find User Success", user);
    }

    @Override
    @Transactional
    public Response<Void> deleteUser(String userId, HttpHeaders headers) {
        log.info("[deleteUser][DELETE USER BY ID][userId: {}]", userId);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            log.error("[deleteUser][User not found][userId: {}]", userId);
            return new Response<>(0, "USER NOT EXISTS", null);
        }

        // Delete auth first, then user record
        deleteUserAuth(userId, headers);
        userRepository.deleteByUserId(userId);
        log.info("[deleteUser][DELETE SUCCESS][userId: {}]", userId);
        return new Response<>(1, "DELETE SUCCESS", null);
    }

    @Override
    @Transactional
    public Response<User> updateUser(User user, HttpHeaders headers) {
        log.info("[updateUser][UPDATE USER: {}]", user.toString());
        User existingUser = userRepository.findByUserId(user.getUserId());
        if (existingUser == null) {
            log.error("[updateUser][User not found][UserId: {}]", user.getUserId());
            return new Response<>(0, "USER NOT EXISTS", null);
        }
        userRepository.deleteByUserId(existingUser.getUserId());
        userRepository.save(user);
        return new Response<>(1, "SAVE USER SUCCESS", user);
    }

    public void deleteUserAuth(String userId, HttpHeaders headers) {
        log.info("[deleteUserAuth][DELETE USER BY ID][userId: {}]", userId);
        authClient.adminDeleteUser(userId, headers);
        log.info("[deleteUserAuth][DELETE USER AUTH SUCCESS][userId: {}]", userId);
    }
}
