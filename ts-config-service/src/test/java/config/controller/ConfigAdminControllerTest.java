package config.controller;

import com.alibaba.fastjson.JSONObject;
import config.entity.Config;
import config.service.ConfigService;
import edu.fudan.common.client.dto.config.ConfigDto;
import config.mapper.ConfigMapper;
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
    @Mock
    private ConfigMapper configMapper;

    private MockMvc mockMvc;

    private Response<ConfigDto> response = new Response<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminConfigController).build();
    }

    @Test
    public void testAddConfig() throws Exception {
        ConfigDto dto = new ConfigDto();
        Response<Config> respEntity = new Response<>();
        Mockito.when(configMapper.toEntity(Mockito.any(ConfigDto.class))).thenReturn(new Config());
        Mockito.when(configService.create(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(respEntity);
        Mockito.when(configMapper.toDtoResponse(respEntity)).thenReturn(response);
        String requestJson = JSONObject.toJSONString(dto);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/config/admin/configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyConfig() throws Exception {
        ConfigDto dto = new ConfigDto();
        Response<Config> respEntity = new Response<>();
        Mockito.when(configMapper.toEntity(Mockito.any(ConfigDto.class))).thenReturn(new Config());
        Mockito.when(configService.update(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(respEntity);
        Mockito.when(configMapper.toDtoResponse(respEntity)).thenReturn(response);
        String requestJson = JSONObject.toJSONString(dto);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/config/admin/configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteConfig() throws Exception {
        Response<Config> respEntity = new Response<>();
        Mockito.when(configService.delete(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(respEntity);
        Mockito.when(configMapper.toDtoResponse(respEntity)).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/config/admin/configs/config_name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }
}
