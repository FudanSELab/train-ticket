package auth.controller;

import edu.fudan.common.client.dto.auth.AuthDto;
import auth.service.UserService;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(JUnit4.class)
public class AuthControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/users/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello"));
    }

    @Test
    public void testCreateDefaultUser() throws Exception {
        AuthDto authDto =  AuthDto.builder().userName("test").password("test00").build();
        Mockito.when(userService.createDefaultAuthUser(Mockito.any(AuthDto.class))).thenReturn(null);
        String requestJson = JSONObject.toJSONString(authDto);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("SUCCESS", JSONObject.parseObject(result, Response.class).getMsg());
    }
}
