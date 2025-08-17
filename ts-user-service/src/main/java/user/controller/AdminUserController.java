package user.controller;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.UserDto;
import user.service.UserService;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Admin endpoints for managing users.
 * This replaces the standalone ts-admin-user-service.
 */
@RestController
@RequestMapping("/api/v1/userservice/admin/users")
public class AdminUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminUser Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Response> getAllUsers(@RequestHeader HttpHeaders headers) {
        LOGGER.info("[getAllUsers][Get all users]");
        return ok(userService.getAllUsers(headers));
    }

    @PutMapping
    public ResponseEntity<Response> updateUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[updateUser][Update User][userName: {}]", userDto.getUserName());
        return ok(userService.updateUser(userDto, headers));
    }

    @PostMapping
    public ResponseEntity<Response> addUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[addUser][Add user][userName: {}]", userDto.getUserName());
        return ok(userService.saveUser(userDto, headers));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        LOGGER.info("[deleteUser][Delete user][userId: {}]", userId);
        return ok(userService.deleteUser(userId, headers));
    }
}
