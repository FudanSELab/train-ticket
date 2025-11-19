package fdse.microservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import fdse.microservice.mapper.StationMapper;
import fdse.microservice.service.StationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mapstruct.factory.Mappers;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class StationControllerTest {

    @InjectMocks
    private StationController stationController;

    @Mock
    private StationService stationService;

    @Spy
    private StationMapper stationMapper = Mappers.getMapper(StationMapper.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stationController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/station/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Station Service ] !"));
    }

    @Test
    public void testQuery() throws Exception {
        Station station = sampleStation();
        List<Station> stations = new ArrayList<>();
        stations.add(station);
        Response<List<Station>> response = new Response<>(1, "Find all content", stations);
        Mockito.when(stationService.query(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/station/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject resultJson = JSONObject.parseObject(result);
        Assert.assertEquals(response.getStatus(), resultJson.getInteger("status"));
        JSONArray dataArray = resultJson.getJSONArray("data");
        Assert.assertEquals(station.getId(), dataArray.getJSONObject(0).getString("id"));
        Assert.assertEquals(station.getName(), dataArray.getJSONObject(0).getString("name"));
        Assert.assertEquals(station.getStayTime(), dataArray.getJSONObject(0).getInteger("stayTime").intValue());
    }

    @Test
    public void testQueryForStationId() throws Exception {
        Response<String> response = new Response<>();
        Mockito.when(stationService.queryForId(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/station/stations/id/station_name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testQueryForIdBatch() throws Exception {
        List<String> stationNameList = new ArrayList<>();
        Response<Map<String, String>> response = new Response<>();
        Mockito.when(stationService.queryForIdBatch(Mockito.anyList(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(stationNameList);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/station/stations/idlist").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testQueryById() throws Exception {
        Response<String> response = new Response<>();
        Mockito.when(stationService.queryById(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/station/stations/name/station_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testQueryForNameBatch() throws Exception {
        List<String> stationIdList = new ArrayList<>();
        Response<List<String>> response = new Response<>();
        Mockito.when(stationService.queryByIdBatch(Mockito.anyList(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(stationIdList);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/station/stations/namelist").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    private Station sampleStation() {
        Station station = new Station("Shanghai", 5);
        station.setId("station_id");
        return station;
    }
}
