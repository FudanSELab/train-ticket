package train.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.client.dto.train.TrainTypeDto;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import train.entity.TrainType;
import train.mapper.TrainTypeMapper;
import train.service.TrainService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TrainControllerTest {

    @InjectMocks
    private TrainController trainController;

    @Mock
    private TrainService trainService;

    @Mock
    private TrainTypeMapper trainTypeMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Train Service ] !"));
    }

    @Test
    public void testQuerySuccess() throws Exception {
        List<TrainType> entities = Collections.singletonList(sampleEntity());
        List<TrainTypeDto> dtos = Collections.singletonList(sampleDto());
        when(trainService.query(any(HttpHeaders.class))).thenReturn(entities);
        when(trainTypeMapper.toDtoList(entities)).thenReturn(dtos);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> response = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(Integer.valueOf(1), response.getStatus());
        Assert.assertEquals("success", response.getMsg());
    }

    @Test
    public void testQueryNoContent() throws Exception {
        List<TrainType> entities = Collections.emptyList();
        List<TrainTypeDto> dtos = Collections.emptyList();
        when(trainService.query(any(HttpHeaders.class))).thenReturn(entities);
        when(trainTypeMapper.toDtoList(entities)).thenReturn(dtos);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("no content", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testRetrieveNotFound() throws Exception {
        when(trainService.retrieve(any(String.class), any(HttpHeaders.class))).thenReturn(null);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes/wrong_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> response = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(Integer.valueOf(0), response.getStatus());
        Assert.assertEquals("here is no TrainType with the trainType id: wrong_id", response.getMsg());
    }

    @Test
    public void testRetrieveSuccess() throws Exception {
        TrainType entity = sampleEntity();
        TrainTypeDto dto = sampleDto();
        when(trainService.retrieve(any(String.class), any(HttpHeaders.class))).thenReturn(entity);
        when(trainTypeMapper.toDto(entity)).thenReturn(dto);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("success", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testRetrieveByNameNotFound() throws Exception {
        when(trainService.retrieveByName(any(String.class), any(HttpHeaders.class))).thenReturn(null);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes/byName/G1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> response = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(Integer.valueOf(0), response.getStatus());
        Assert.assertEquals("here is no TrainType with the trainType name: G1", response.getMsg());
    }

    @Test
    public void testRetrieveByNameSuccess() throws Exception {
        TrainType entity = sampleEntity();
        when(trainService.retrieveByName(any(String.class), any(HttpHeaders.class))).thenReturn(entity);
        when(trainTypeMapper.toDto(entity)).thenReturn(sampleDto());

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/train/trainTypes/byName/G1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("success", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testRetrieveByNamesSuccess() throws Exception {
        List<TrainType> entities = Collections.singletonList(sampleEntity());
        List<TrainTypeDto> dtos = Collections.singletonList(sampleDto());
        when(trainService.retrieveByNames(anyList(), any(HttpHeaders.class))).thenReturn(entities);
        when(trainTypeMapper.toDtoList(entities)).thenReturn(dtos);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/train/trains/byNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"G1\"]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("success", JSONObject.parseObject(result, Response.class).getMsg());
    }

    @Test
    public void testRetrieveByNamesNotFound() throws Exception {
        when(trainService.retrieveByNames(anyList(), any(HttpHeaders.class))).thenReturn(null);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/train/trains/byNames")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"G1\"]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("here is no TrainTypes with the trainType names: [G1]",
                JSONObject.parseObject(result, Response.class).getMsg());
    }

    private TrainType sampleEntity() {
        TrainType type = new TrainType();
        type.setId("id");
        type.setName("G1");
        type.setEconomyClass(100);
        type.setConfortClass(50);
        type.setAverageSpeed(300);
        return type;
    }

    private TrainTypeDto sampleDto() {
        TrainTypeDto dto = new TrainTypeDto();
        dto.setId("id");
        dto.setName("G1");
        dto.setEconomyClass(100);
        dto.setConfortClass(50);
        dto.setAverageSpeed(300);
        return dto;
    }
}
