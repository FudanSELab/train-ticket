package auth.controller;

import auth.entity.User;
import auth.service.UserService;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin endpoints for managing users in auth service.
 */
@RestController
@RequestMapping("/api/v1/auth/admin/users")
@Slf4j
public class UserAdminController {

    @Autowired
    private UserService userService;

    /**
     * Get all users.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(@RequestHeader HttpHeaders headers) {
        log.info("[Admin][getAllUser][Get all users]");
        return ResponseEntity.ok(userService.getAllUser(headers));
    }

    /**
     * Delete user by id.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<String>> deleteUserById(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        log.info("[Admin][deleteUserById][Delete user][userId: {}]", userId);
        return ResponseEntity.ok(userService.deleteByUserId(userId, headers));
    }
}
