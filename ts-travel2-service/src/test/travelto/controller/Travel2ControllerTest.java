package travelto.controller;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import travelto.Travel2Application;
import travelto.entity.TravelInfo;
import travelto.entity.TripInfo;
import travelto.init.InitData;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static travelto.init.InitData.*;

/**
 * Travel2Controller Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 24, 2019</pre>
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Travel2Application.class)
public class Travel2ControllerTest {

    private MockMvc mvc;
    @Autowired
    WebApplicationContext context;
    HttpHeaders headers;


    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        headers = new HttpHeaders();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: home(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testHome() throws Exception {
        mvc.perform(get("/api/v1/travel2service/welcome"))
                .andExpect(status().isOk());
    }

    /**
     * Method: getTrainTypeByTripId(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetTrainTypeByTripId() throws Exception {
        mvc.perform(get("/api/v1/travel2service/train_types/Z1234"))
                .andExpect(status().isOk());
    }

    /**
     * Method: getRouteByTripId(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetRouteByTripId() throws Exception {
        mvc.perform(get("/api/v1/travel2service/routes/Z1234"))
                .andExpect(status().isOk());
    }

    /**
     * Method: getTripsByRouteId(@RequestBody List<String> routeIds, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testGetTripsByRouteId() throws Exception {
        List<String> routeIds = new ArrayList<>();
        routeIds.add("0b23bd3e-876a-4af3-b920-c50a90c90b04");
        routeIds.add("9fc9c261-3263-4bfa-82f8-bb44e06b2f52");

        String data = JSON.toJSONString(routeIds);
        mvc.perform(post("/api/v1/travel2service/trips/routes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(data)
                .param("headers", JSON.toJSONString(headers)))
                .andExpect(status().isOk());

    }

    /**
     * Method: createTrip(@RequestBody TravelInfo routeIds, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testCreateTrip() throws Exception {
        TravelInfo info1 = InitData.buildTravelInfo("Z2234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c99999",
                SH, SH, BJ, "Mon May 04 09:51:52 GMT+0800 2013", "Mon May 04 15:51:52 GMT+0800 2013", null);
        TravelInfo info2 = InitData.buildTravelInfo("Z1234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90b04",
                SH, SH, BJ, "Mon May 04 09:51:52 GMT+0800 2013", "Mon May 04 15:51:52 GMT+0800 2013", null);
        //test case 1
        mvc.perform(post("/api/v1/travel2service/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(info1))
                .param("headers", JSON.toJSONString(headers)))
                .andExpect(status().isCreated());
        //test case 2
        mvc.perform(post("/api/v1/travel2service/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(info2))
                .param("headers", JSON.toJSONString(headers)))
                .andExpect(status().isCreated());
    }

    /**
     * Method: retrieve(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testRetrieve() throws Exception {
        mvc.perform(get("/api/v1/travel2service/trips/Z1234"))
                .andExpect(status().isOk());
    }

    /**
     * Method: updateTrip(@RequestBody TravelInfo info, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testUpdateTrip() throws Exception {
        TravelInfo info1 = InitData.buildTravelInfo("Z3234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90000",
                SH, SH, BJ, "Mon May 04 09:51:52 GMT+0800 2013", "Mon May 04 15:51:52 GMT+0800 2013", null);
        TravelInfo info2 = InitData.buildTravelInfo("Z1234", "ZhiDa", "0b23bd3e-876a-4af3-b920-c50a90c90b04",
                SH, SH, BJ, "Mon May 04 09:51:52 GMT+0800 2013", "Mon May 04 15:51:52 GMT+0800 2013", null);
        //test case 1
        mvc.perform(put("/api/v1/travel2service/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(info1))
                .param("headers", JSON.toJSONString(headers)))
                .andExpect(status().isOk());
        //test case 2
        mvc.perform(put("/api/v1/travel2service/trips")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(info2))
                .param("headers", JSON.toJSONString(headers)))
                .andExpect(status().isOk());
    }

    /**
     * Method: deleteTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testDeleteTrip() throws Exception {
        //test case 1
        mvc.perform(delete("/api/v1/travel2service/trips/Z4234"))
                .andExpect(status().isOk());
        //test case 2
        mvc.perform(delete("/api/v1/travel2service/trips/Z1236"))
                .andExpect(status().isOk());
    }

    /**
     * Method: queryInfo(@RequestBody TripInfo info, @RequestHeader HttpHeaders headers)
     */
    @Test
    public void testQueryInfo() throws Exception {
        TripInfo info1 = new TripInfo();
        mvc.perform(post("/api/v1/travel2service/trips/left")
                .param("header", JSON.toJSONString(new HttpHeaders()))
                .content(JSON.toJSONString(info1))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        TripInfo info2 = new TripInfo();
        info2.setStartingPlace("Shang Hai");
        info2.setEndPlace("Tai Yuan");
        info2.setDepartureTime(str2Date("Mon May 01 09:51:52 GMT+0800 2020"));
        mvc.perform(post("/api/v1/travel2service/trips/left")
                .param("header", JSON.toJSONString(new HttpHeaders()))
                .content(JSON.toJSONString(info2))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        TripInfo info3 = new TripInfo();
        info3.setStartingPlace("Shang Hai");
        info3.setEndPlace("Tai Yuan");
        info3.setDepartureTime(str2Date("Mon May 01 09:51:52 GMT+0800 2013"));
        mvc.perform(post("/api/v1/travel2service/trips/left")
                .param("header", JSON.toJSONString(new HttpHeaders()))
                .content(JSON.toJSONString(info3))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    /**
     * Method: queryAll(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testQueryAll() throws Exception {
        mvc.perform(get("/api/v1/travel2service/trips"))
                .andExpect(status().isOk());
    }

    /**
     * Method: adminQueryAll(@RequestHeader HttpHeaders headers)
     */
    @Test
    public void testAdminQueryAll() throws Exception {
        mvc.perform(get("/api/v1/travel2service/admin_trip"))
                .andExpect(status().isOk());
    }


} 
