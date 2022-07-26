package food.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.util.Response;
import food.service.StationFoodService;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class StationFoodControllerTest {

    @InjectMocks
    private StationFoodController stationFoodController;

    @Mock
    private StationFoodService stationFoodService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stationFoodController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foodmapservice/foodstores/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Food store Service ] !"));
    }

    @Test
    public void testGetAllFoodStores() throws Exception {
        Mockito.when(stationFoodService.listFoodStores(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foodmapservice/foodstores"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetFoodStoresOfStation() throws Exception {
        Mockito.when(stationFoodService.listFoodStoresByStationName(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/foodmapservice/foodstores/station_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetFoodStoresByStationNames() throws Exception {
        List<String> stationIdList = new ArrayList<>();
        Mockito.when(stationFoodService.getFoodStoresByStationNames(Mockito.anyList())).thenReturn(response);
        String requestJson = JSONObject.toJSONString(stationIdList);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/foodmapservice/foodstores").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

}
