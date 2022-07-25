package adminbasic.controller;

import adminbasic.entity.*;
import adminbasic.service.AdminBasicInfoService;
import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.entity.Config;
import edu.fudan.common.entity.Contacts;
import edu.fudan.common.entity.Station;
import edu.fudan.common.entity.TrainType;
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
public class AdminBasicInfoControllerTest {

    @InjectMocks
    private AdminBasicInfoController adminBasicInfoController;

    @Mock
    private AdminBasicInfoService adminBasicInfoService;
    private MockMvc mockMvc;
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminBasicInfoController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ AdminBasicInfo Service ] !"));
    }

    @Test
    public void testGetAllContacts() throws Exception {
        Mockito.when(adminBasicInfoService.getAllContacts(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/adminbasic/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteContacts() throws Exception {
        Mockito.when(adminBasicInfoService.deleteContact(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/adminbasicservice/adminbasic/contacts/contactsId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyContacts() throws Exception {
        Contacts mci = new Contacts();
        Mockito.when(adminBasicInfoService.modifyContact(Mockito.any(Contacts.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(mci);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/adminbasicservice/adminbasic/contacts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddContacts() throws Exception {
        Contacts c = new Contacts();
        Mockito.when(adminBasicInfoService.addContact(Mockito.any(Contacts.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(c);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adminbasicservice/adminbasic/contacts").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetAllStations() throws Exception {
        Mockito.when(adminBasicInfoService.getAllStations(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/adminbasic/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteStation() throws Exception {
        Station s = new Station();
        Mockito.when(adminBasicInfoService.deleteStation(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(s);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/adminbasicservice/adminbasic/stations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyStation() throws Exception {
        Station s = new Station();
        Mockito.when(adminBasicInfoService.modifyStation(Mockito.any(Station.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(s);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/adminbasicservice/adminbasic/stations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddStation() throws Exception {
        Station s = new Station();
        Mockito.when(adminBasicInfoService.addStation(Mockito.any(Station.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(s);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adminbasicservice/adminbasic/stations").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetAllTrains() throws Exception {
        Mockito.when(adminBasicInfoService.getAllTrains(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/adminbasic/trains"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteTrain() throws Exception {
        Mockito.when(adminBasicInfoService.deleteTrain(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/adminbasicservice/adminbasic/trains/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyTrain() throws Exception {
        TrainType t = new TrainType();
        Mockito.when(adminBasicInfoService.modifyTrain(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(t);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/adminbasicservice/adminbasic/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddTrain() throws Exception {
        TrainType t = new TrainType();
        Mockito.when(adminBasicInfoService.addTrain(Mockito.any(TrainType.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(t);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adminbasicservice/adminbasic/trains").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetAllConfigs() throws Exception {
        Mockito.when(adminBasicInfoService.getAllConfigs(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/adminbasic/configs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeleteConfig() throws Exception {
        Mockito.when(adminBasicInfoService.deleteConfig(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/adminbasicservice/adminbasic/configs/name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyConfig() throws Exception {
        Config c = new Config();
        Mockito.when(adminBasicInfoService.modifyConfig(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(c);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/adminbasicservice/adminbasic/configs").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddConfig() throws Exception {
        Config c = new Config();
        Mockito.when(adminBasicInfoService.addConfig(Mockito.any(Config.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(c);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adminbasicservice/adminbasic/configs").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testGetAllPrices() throws Exception {
        Mockito.when(adminBasicInfoService.getAllPrices(Mockito.any(HttpHeaders.class))).thenReturn(response);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/adminbasicservice/adminbasic/prices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testDeletePrice() throws Exception {
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.deletePrice(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(pi);
        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/adminbasicservice/adminbasic/prices").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testModifyPrice() throws Exception {
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.modifyPrice(Mockito.any(PriceInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(pi);
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/adminbasicservice/adminbasic/prices").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }

    @Test
    public void testAddPrice() throws Exception {
        PriceInfo pi = new PriceInfo();
        Mockito.when(adminBasicInfoService.addPrice(Mockito.any(PriceInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        String requestJson = JSONObject.toJSONString(pi);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adminbasicservice/adminbasic/prices").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(response, JSONObject.parseObject(result, Response.class));
    }
}
