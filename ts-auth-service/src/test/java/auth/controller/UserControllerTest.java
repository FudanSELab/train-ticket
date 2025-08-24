package auth.controller;

import edu.fudan.common.client.dto.auth.BasicAuthDto;
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

import javax.servlet.http.HttpServletRequest;
import edu.fudan.common.client.dto.auth.TokenDto;

@RunWith(JUnit4.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    // TokenService removed
    private MockMvc mockMvc;
    private final Response<TokenDto> response = new Response<>();

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
    public void testGetToken() throws Exception {
        BasicAuthDto dao = new BasicAuthDto();
        Mockito.when(userService.getToken(Mockito.any(HttpServletRequest.class), Mockito.any(BasicAuthDto.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(dao);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/users/login").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    // Tests for admin endpoints moved to UserAdminControllerTest

}
