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
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import security.service.SecurityService;

/**
 * Tests for {@link SecurityAdminController} endpoints.
 */
@RunWith(JUnit4.class)
public class SecurityAdminControllerTest {

    @InjectMocks
    private SecurityAdminController securityAdminController;

    @Mock
    private SecurityService securityService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(securityAdminController).build();
    }

    @Test
    public void testUpdateMaxOrderOneHour() throws Exception {
        Response<Boolean> response = new Response<>(1, "Success", true);
        Mockito.when(securityService.updateMaxOrderOneHour(Mockito.eq(5), Mockito.any(HttpHeaders.class))).thenReturn(response);

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/security/admin/config/maxOrderOneHour/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testUpdateMaxOrderNotUse() throws Exception {
        Response<Boolean> response = new Response<>(1, "Success", true);
        Mockito.when(securityService.updateMaxOrderNotUse(Mockito.eq(3), Mockito.any(HttpHeaders.class))).thenReturn(response);

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/security/admin/config/maxOrderNotUse/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }
}
