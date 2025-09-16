package config.controller;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import edu.fudan.common.client.dto.config.ConfigDto;
import config.mapper.ConfigMapper;
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
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(JUnit4.class)
public class ConfigControllerTest {

    @InjectMocks
    private ConfigController configController;

    @Mock
    private ConfigService configService;
    @Mock
    private ConfigMapper configMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(configController).build();
    }

    @Test
    public void testQueryAll() throws Exception {
        Response<List<Config>> entityResp = new Response<>(1, "Success", new ArrayList<>());
        Response<List<ConfigDto>> dtoResp = new Response<>(1, "Success", new ArrayList<>());
        Mockito.when(configService.queryAll(Mockito.any(HttpHeaders.class))).thenReturn(entityResp);
        Mockito.when(configMapper.toDtoListResponse(entityResp)).thenReturn(dtoResp);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/config/configs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Response<List<ConfigDto>> actual = JSON.parseObject(result, new TypeReference<Response<List<ConfigDto>>>() {});
        Assert.assertEquals(dtoResp, actual);
    }

    @Test
    public void testRetrieve() throws Exception {
        Response<Config> entityResp = new Response<>(1, "Success", new Config("config_name", "config_value", "config_description"));
        Response<ConfigDto> dtoResp = new Response<>(1, "Success", new ConfigDto("config_name", "config_value", "config_description"));
        Mockito.when(configService.query(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(entityResp);
        Mockito.when(configMapper.toDtoResponse(entityResp)).thenReturn(dtoResp);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/config/configs/config_name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Response<ConfigDto> actual = JSON.parseObject(result, new TypeReference<Response<ConfigDto>>() {});
        Assert.assertEquals(dtoResp, actual);
    }

}
