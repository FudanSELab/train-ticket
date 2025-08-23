package security.controller;

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
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import security.service.SecurityService;

@RunWith(JUnit4.class)
public class SecurityControllerTest {

    @InjectMocks
    private SecurityController securityController;

    @Mock
    private SecurityService securityService;
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(securityController).build();
    }
    
    @Test
    public void testCheck() throws Exception {
        Response<String> response = new Response<>(1, "Success", "account_id");
        Mockito.when(securityService.check(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/security/securityConfigs/account_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
