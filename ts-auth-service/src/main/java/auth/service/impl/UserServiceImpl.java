package auth.service.impl;

import auth.constant.AuthConstant;
import auth.constant.InfoConstant;
import edu.fudan.common.client.dto.auth.AuthDto;
import auth.entity.User;
import auth.exception.UserOperationException;
import auth.repository.UserRepository;
import auth.service.UserService;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import edu.fudan.common.client.dto.auth.BasicAuthDto;
import edu.fudan.common.client.dto.auth.TokenDto;
import auth.security.jwt.JWTProvider;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import auth.service.VerifyCodeService;
import javax.servlet.http.HttpServletRequest;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author fdse
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUser(HttpHeaders headers) {
        return (List<User>) userRepository.findAll();
    }

    /**
     * create  a user with default role of user
     *
     * @param dto
     * @return
     */
    @Override
    public User createDefaultAuthUser(AuthDto dto) {
        log.info("[createDefaultAuthUser][Register User Info][AuthDto name: {}]", dto.getUserName());
        User user = User.builder()
                .username(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(new HashSet<>(Arrays.asList(AuthConstant.ROLE_USER)))
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Response<String> deleteByUserId(String userId, HttpHeaders headers) {
        log.info("[deleteByUserId][DELETE USER][user id: {}]", userId);
        userRepository.deleteByUserId(userId);
        return new Response<>(1, "DELETE USER SUCCESS", userId);
    }

    @Override
    public Response<TokenDto> getToken(HttpServletRequest request, BasicAuthDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String verifyCode = dto.getVerificationCode();
        log.info("LOGIN USER :{} __ {}", username, verifyCode);

        if (StringUtils.isEmpty(verifyCode)) {
            return new Response<>(0, "Verification code is required.", null);
        }

        if (!verifyCodeService.verifyCode(request, verifyCode)) {
            return new Response<>(0, "Verification code is incorrect.", null);
        }

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(upat);
        } catch (Exception e) {
            log.warn("[getToken][Incorrect username or password][username: {}]", username);
            return new Response<>(0, "Incorrect username or password.", null);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserOperationException(MessageFormat.format(InfoConstant.USER_NAME_NOT_FOUND_1, username)));

        String token = jwtProvider.createToken(user);
        log.info("[getToken][success][USER TOKEN: {} USER ID: {}]", token, user.getUserId());
        return new Response<>(1, "login success", new TokenDto(user.getUserId(), username, token));
    }
}
