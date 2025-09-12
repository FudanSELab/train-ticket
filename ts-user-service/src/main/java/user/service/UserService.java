package user.service;

import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.UUID;
import java.util.List;
import user.entity.User;

/**
 * @author fdse
 */
public interface UserService {
  Response<User> createUser(User user, String password, HttpHeaders headers);

  Response<List<User>> getAllUsers(HttpHeaders headers);

  Response<User> findByUserId(String userId, HttpHeaders headers);

  Response<Void> deleteUser(String userId, HttpHeaders headers);

  Response<User> updateUser(User user, HttpHeaders headers);
}
