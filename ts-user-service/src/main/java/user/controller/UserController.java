package user.controller;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import user.service.UserService;
import user.entity.User;
import user.mapper.UserMapper;
import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.security.jwt.JWTUtil;
import javax.servlet.http.HttpServletRequest;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String testHello() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<Response<User>> registerUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        log.info("[registerUser][Register user][UserName: {}]",userDto.getUserName());
        User user = userMapper.toEntity(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user, userDto.getPassword(), headers));
    }

    @GetMapping("/me")
    public ResponseEntity<Response<User>> getMe(HttpServletRequest request, @RequestHeader HttpHeaders headers) {
        String userId = JWTUtil.getUserIdFromHeader(request);
        log.info("[getMe][Get current user][userId: {}]", userId);
        return ok(userService.findByUserId(userId, headers));
    }

    @PutMapping("/me")
    public ResponseEntity<Response<User>> updateMe(@RequestBody UserDto userDto,
                                                   HttpServletRequest request,
                                                   @RequestHeader HttpHeaders headers) {
        String userId = JWTUtil.getUserIdFromHeader(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new Response<>(0, "Unauthorized", null));
        }

        // Fill or validate userId
        if (userDto.getUserId() == null || userDto.getUserId().isEmpty()) {
            userDto.setUserId(userId);
        } else if (!userId.equals(userDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Response<>(0, "Forbidden", null));
        }

        log.info("[updateMe][Update me][UserId: {}]", userId);
        return ok(userService.updateUser(userMapper.toEntity(userDto), headers));
    }
}
