package auth.controller;

import auth.entity.User;
import auth.service.UserService;
import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.client.dto.user.UserDto;
import edu.fudan.common.util.Response;
import auth.mapper.UserMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Tests for {@link UserAdminController}.
 */
@RunWith(JUnit4.class)
public class UserAdminControllerTest {

    @InjectMocks
    private UserAdminController userAdminController;

    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    private MockMvc mockMvc;

    private HttpHeaders headers;

    private final Response<String> response = new Response<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAdminController).build();
        headers = new HttpHeaders();
        headers.add("test", "true");
    }

    @Test
    public void testGetAllUser() throws Exception {
        List<User> userList = new ArrayList<>();
        List<UserDto> dtoList = new ArrayList<>();
        Mockito.when(userService.getAllUser(Mockito.any(HttpHeaders.class))).thenReturn(userList);
        Mockito.when(userMapper.toDtoList(userList)).thenReturn(dtoList);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/admin/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(dtoList, JSONObject.parseObject(result, List.class));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(userService.deleteByUserId(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn((Response<String>) response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/auth/admin/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }
}
