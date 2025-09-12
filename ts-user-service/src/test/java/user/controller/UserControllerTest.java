package user.controller;

import com.alibaba.fastjson.JSONObject;

import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import user.service.UserService;

import user.entity.User;

@RunWith(JUnit4.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    @Mock
    private user.mapper.UserMapper userMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .userName("tom")
                .password("pwd")
                .gender(0)
                .documentType(1)
                .documentNum("123456")
                .email("a@b.com")
                .build();
        User user = User.builder()
                .userName("tom")
                .gender(0)
                .documentType(1)
                .documentNum("123456")
                .email("a@b.com")
                .build();
        Response<User> resp = new Response<>(1, "REGISTER USER SUCCESS", user);

        Mockito.when(userMapper.toEntity(Mockito.any(UserDto.class))).thenReturn(user);
        Mockito.when(userService.createUser(Mockito.any(User.class), Mockito.eq("pwd"), Mockito.any(HttpHeaders.class)))
                .thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(userService, Mockito.times(1))
                .createUser(Mockito.any(User.class), Mockito.eq("pwd"), Mockito.any(HttpHeaders.class));
    }
}
