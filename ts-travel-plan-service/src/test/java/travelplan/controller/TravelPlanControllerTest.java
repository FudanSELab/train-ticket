package travelplan.controller;

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
import travelplan.entity.TransferTravelInfo;
import edu.fudan.common.entity.TripInfo;
import travelplan.service.TravelPlanService;

@RunWith(JUnit4.class)
public class TravelPlanControllerTest {

    @InjectMocks
    private TravelPlanController travelPlanController;

    @Mock
    private TravelPlanService travelPlanService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(travelPlanController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/travelplanservice/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ TravelPlan Service ] !"));
    }

    @Test
    public void testGetTransferResult() throws Exception {
        TransferTravelInfo info = new TransferTravelInfo();
        Mockito.when(travelPlanService.getTransferSearch(Mockito.any(TransferTravelInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/travelplanservice/travelPlan/transferResult").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetByCheapest() throws Exception {
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getCheapest(Mockito.any(TripInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(queryInfo);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/travelplanservice/travelPlan/cheapest").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetByQuickest() throws Exception {
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getQuickest(Mockito.any(TripInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(queryInfo);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/travelplanservice/travelPlan/quickest").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetByMinStation() throws Exception {
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getMinStation(Mockito.any(TripInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(queryInfo);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/travelplanservice/travelPlan/minStation").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
