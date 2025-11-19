package fdse.microservice.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.client.dto.station.StationDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(JUnit4.class)
public class AdminStationControllerTest {

    @InjectMocks
    private AdminStationController adminStationController;

    @Mock
    private StationService stationService;

    @Spy
    private StationMapper stationMapper = Mappers.getMapper(StationMapper.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminStationController).build();
    }

    @Test
    public void testCreate() throws Exception {
        Station station = sampleStation();
        Response<Station> response = new Response<>(1, "Create success", station);
        Mockito.when(stationService.create(Mockito.any(Station.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        StationDto stationDto = stationMapper.toDto(station);
        String requestJson = JSONObject.toJSONString(stationDto);
        String result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/station/admin/stations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject resultJson = JSONObject.parseObject(result);
        Assert.assertEquals(response.getStatus(), resultJson.getInteger("status"));
        JSONObject dataJson = resultJson.getJSONObject("data");
        Assert.assertEquals(stationDto.getName(), dataJson.getString("name"));
        Assert.assertEquals(stationDto.getStayTime(), dataJson.getInteger("stayTime").intValue());
    }

    @Test
    public void testUpdate() throws Exception {
        Station station = sampleStation();
        Response<Station> response = new Response<>(1, "Update success", station);
        Mockito.when(stationService.update(Mockito.any(Station.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        StationDto stationDto = stationMapper.toDto(station);
        String requestJson = JSONObject.toJSONString(stationDto);
        String result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/station/admin/stations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject resultJson = JSONObject.parseObject(result);
        Assert.assertEquals(response.getStatus(), resultJson.getInteger("status"));
        JSONObject dataJson = resultJson.getJSONObject("data");
        Assert.assertEquals(stationDto.getId(), dataJson.getString("id"));
    }

    @Test
    public void testDelete() throws Exception {
        Station station = sampleStation();
        Response<Station> response = new Response<>(1, "Delete success", station);
        Mockito.when(stationService.delete(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/station/admin/stations/{stationId}", station.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONObject resultJson = JSONObject.parseObject(result);
        Assert.assertEquals(response.getStatus(), resultJson.getInteger("status"));
        JSONObject dataJson = resultJson.getJSONObject("data");
        Assert.assertEquals(station.getId(), dataJson.getString("id"));
    }

    private Station sampleStation() {
        Station station = new Station("Shanghai", 5);
        station.setId("station_id");
        return station;
    }
}

