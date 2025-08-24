package auth.controller;


import edu.fudan.common.client.dto.auth.AuthDto;
import edu.fudan.common.client.dto.auth.BasicAuthDto;
import edu.fudan.common.client.dto.auth.TokenDto;
import auth.constant.InfoConstant;
import auth.exception.UserOperationException;
import auth.service.UserService;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author fdse
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

    @PostMapping
    public HttpEntity<Response<AuthDto>> createDefaultUser(@RequestBody AuthDto authDto) {
        log.info("[createDefaultUser][Create default auth user with authDto][AuthDto: {}]", authDto.toString());
        validateAuthDto(authDto);
        userService.createDefaultAuthUser(authDto);
        Response<AuthDto> response = new Response<>(1, "SUCCESS", authDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void validateAuthDto(AuthDto dto) {
        if (dto == null) {
            throw new UserOperationException("AuthDto cannot be null");
        }
        if (dto.getUserName() == null || dto.getUserName().isEmpty()) {
            throw new UserOperationException(String.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.USERNAME));
        }
        if (dto.getPassword() == null) {
            throw new UserOperationException(String.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.PASSWORD));
        }
        if (dto.getPassword().length() < 6) {
            throw new UserOperationException(String.format(InfoConstant.PASSWORD_LEAST_CHAR_1, 6));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response<TokenDto>> getToken(@RequestBody BasicAuthDto dto, HttpServletRequest request) {
        log.info("Login request of username: {}", dto.getUsername());
        try {
            Response<TokenDto> res = userService.getToken(request, dto);
            return ResponseEntity.ok(res);
        } catch (UserOperationException e) {
            log.error("[getToken][tokenService.getToken error][UserOperationException, message: {}]", e.getMessage());
            return ResponseEntity.ok(new Response<>(0, "get token error", null));
        }
    }
}
