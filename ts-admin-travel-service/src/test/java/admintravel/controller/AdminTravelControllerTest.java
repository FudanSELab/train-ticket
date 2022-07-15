package admintravel.controller;

import admintravel.service.AdminTravelService;
import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.entity.TravelInfo;
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
public class AdminTravelControllerTest {

    @InjectMocks
    private AdminTravelController adminTravelController;

    @Mock
    private AdminTravelService adminTravelService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminTravelController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admintravelservice/welcome"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllTravels() throws Exception {
        Mockito.when(adminTravelService.getAllTravels(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admintravelservice/admintravel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddTravel() throws Exception {
        TravelInfo request = new TravelInfo();
        Mockito.when(adminTravelService.addTravel(Mockito.any(TravelInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(request);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admintravelservice/admintravel").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testUpdateTravel() throws Exception {
        TravelInfo request = new TravelInfo();
        Mockito.when(adminTravelService.updateTravel(Mockito.any(TravelInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(request);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admintravelservice/admintravel").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteTravel() throws Exception {
        Mockito.when(adminTravelService.deleteTravel(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admintravelservice/admintravel/trip_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
