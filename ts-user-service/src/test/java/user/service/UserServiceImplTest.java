package user.service;

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
import org.springframework.http.*;
import edu.fudan.common.client.AuthClient;
import edu.fudan.common.client.dto.auth.AuthDto;
import user.entity.User;
import user.repository.UserRepository;
import user.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthClient authClient;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .userName("user_name")
                .gender(0)
                .documentType(1)
                .documentNum("0123456789")
                .email("0123456789")
                .build();
        Mockito.when(userRepository.findByUserName(Mockito.anyString())).thenReturn(null);

        // mock createDefaultAuthUser()
        Mockito.when(authClient.createDefaultUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new Response<>(1, "Success", new AuthDto()));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Response<User> result = userServiceImpl.createUser(user, "xxx", headers);
        Assert.assertEquals(new Response<>(1, "REGISTER USER SUCCESS", user), result);
    }

    @Test
    public void testGetAllUsers1() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Response<List<User>> result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(1, "Success", users), result);
    }

    @Test
    public void testGetAllUsers2() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
        Response<List<User>> result = userServiceImpl.getAllUsers(headers);
        Assert.assertEquals(new Response<>(1, "Success", new ArrayList<>()), result);
    }

    @Test
    public void testFindByUserId1() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(user);
        Response<User> result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(1, "Find User Success", user), result);
    }

    @Test
    public void testFindByUserId2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        Response<?> result = userServiceImpl.findByUserId(userId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "No User", null), result);
    }

    @Test
    public void testDeleteUser1() {
        String userId = UUID.randomUUID().toString();
        User user = new User();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(user);
        Mockito.when(authClient.adminDeleteUser(Mockito.eq(userId), Mockito.any(HttpHeaders.class)))
                .thenReturn(new Response<>(1, "DELETE SUCCESS", null));
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.anyString());
        Response<?> result = userServiceImpl.deleteUser(userId, headers);
        Assert.assertEquals(new Response<>(1, "DELETE SUCCESS", null), result);
    }

    @Test
    public void testDeleteUser2() {
        UUID userId = UUID.randomUUID();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(null);
        Response<?> result = userServiceImpl.deleteUser(userId.toString(), headers);
        Assert.assertEquals(new Response<>(0, "USER NOT EXISTS", null), result);
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User();
        User oldUser = new User();
        Mockito.when(userRepository.findByUserId(Mockito.anyString())).thenReturn(oldUser);
        Mockito.doNothing().doThrow(new RuntimeException()).when(userRepository).deleteByUserId(Mockito.anyString());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(null);
        Response<?> result = userServiceImpl.updateUser(newUser, headers);
        Assert.assertEquals("SAVE USER SUCCESS", result.getMsg());
    }

    @Test
    public void testDeleteUserAuth() {
        UUID userId = UUID.randomUUID();
        Mockito.when(authClient.adminDeleteUser(Mockito.eq(userId.toString()), Mockito.any(HttpHeaders.class)))
                .thenReturn(new Response<>(1, "DELETE SUCCESS", null));
        userServiceImpl.deleteUserAuth(userId.toString(), headers);
        Mockito.verify(authClient, Mockito.times(1))
                .adminDeleteUser(Mockito.eq(userId.toString()), Mockito.any(HttpHeaders.class));
    }
}
