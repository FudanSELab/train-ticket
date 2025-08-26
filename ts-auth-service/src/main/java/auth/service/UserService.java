package auth.service;

import edu.fudan.common.client.dto.auth.AuthDto;
import edu.fudan.common.client.dto.auth.TokenDto;
import auth.entity.User;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;
import edu.fudan.common.client.dto.auth.BasicAuthDto;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fdse
 */
public interface UserService {

    /**
     * save user
     *
     * @param user user
     * @return user
     */
    User saveUser(User user);

    /**
     * get all users
     *
     * @param headers headers
     * @return List<User>
     */
    List<User> getAllUser(HttpHeaders headers);

    /**
     * create default auth user
     *
     * @param dto dto
     * @return user
     */
    User createDefaultAuthUser(AuthDto dto);

    /**
     * delete by user id
     *
     * @param userId user id
     * @param headers headers
     * @return Response
     */
    Response<String> deleteByUserId(String userId, HttpHeaders headers);

    /**
     * login and get token
     * @param dto basic auth dto
     * @param headers http headers
     * @return Response containing token dto
     */
    Response<TokenDto> getToken(HttpServletRequest request, BasicAuthDto dto);

}
