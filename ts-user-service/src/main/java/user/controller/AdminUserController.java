package user.controller;

import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import user.service.UserService;
import user.entity.User;
import user.mapper.UserMapper;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Admin endpoints for managing users.
 * This replaces the standalone ts-admin-user-service.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminUser Service ] !";
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<Response<User>> getUserByUserId(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        log.info("[getUserByUserId][Get user by user id][UserId: {}]",userId);
        return ok(userService.findByUserId(userId, headers));
    }

    @GetMapping
    public ResponseEntity<Response<List<User>>> getAllUsers(@RequestHeader HttpHeaders headers) {
        log.info("[getAllUsers][Get all users]");
        return ok(userService.getAllUsers(headers));
    }

    @PutMapping
    public ResponseEntity<Response<User>> updateUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        log.info("[updateUser][Update User][userName: {}]", userDto.getUserName());
        User user = userMapper.toEntity(userDto);
        return ok(userService.updateUser(user, headers));
    }

    @PostMapping
    public ResponseEntity<Response<User>> addUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        log.info("[addUser][Add user][userName: {}]", userDto.getUserName());
        User user = userMapper.toEntity(userDto);
        return ok(userService.createUser(user, userDto.getPassword(), headers));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        log.info("[deleteUser][Delete user][userId: {}]", userId);
        return ok(userService.deleteUser(userId, headers));
    }
}
