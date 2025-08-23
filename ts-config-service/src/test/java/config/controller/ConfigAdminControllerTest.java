package config.controller;

import com.alibaba.fastjson.JSONObject;
import config.entity.Config;
import config.service.ConfigService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Tests for AdminConfigController CRUD endpoints.
 */
@RunWith(JUnit4.class)
public class ConfigAdminControllerTest {

    @InjectMocks
    private AdminConfigController adminConfigController;

    @Mock
    private ConfigService configService;

    private MockMvc mockMvc;

    private Response<Config> response = new Response<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminConfigController).build();
    }

    @Test
    public void testAddConfig() throws Exception {
        Config info = new Config();
        Mockito.when(configService.create(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/config/admin/configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyConfig() throws Exception {
        Config info = new Config();
        Mockito.when(configService.update(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/config/admin/configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteConfig() throws Exception {
        Mockito.when(configService.delete(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/config/admin/configs/config_name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }
}
