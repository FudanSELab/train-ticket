package auth.service;

import edu.fudan.common.client.dto.auth.AuthDto;
import auth.entity.User;
import auth.repository.UserRepository;
import auth.service.impl.UserServiceImpl;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;
    @Mock
    protected PasswordEncoder passwordEncoder;

    private final HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        Assert.assertEquals(null, userServiceImpl.saveUser(user));
    }

    @Test
    public void testGetAllUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Assert.assertEquals(userList, userServiceImpl.getAllUser(headers));
    }

    @Test
    public void testCreateDefaultAuthUser() {
        AuthDto dto = new AuthDto(UUID.randomUUID().toString(), "username", "password");
        User user = new User();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("password");
        Assert.assertEquals(user, userServiceImpl.createDefaultAuthUser(dto));
    }

    @Test
    public void testDeleteByUserId() {
        UUID userId = UUID.randomUUID();
        Mockito.doNothing().when(userRepository).deleteByUserId(userId.toString());
        Response<String> expected = new Response<>(1, "DELETE USER SUCCESS", userId.toString());
        Assert.assertEquals(expected, userServiceImpl.deleteByUserId(userId.toString(), headers));
    }

}
