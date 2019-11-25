package preserveother.controller;

import com.alibaba.fastjson.JSONObject;
import consign.entity.Consign;
import consign.service.ConsignService;
import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import preserveother.PreserveOtherApplication;
import preserveother.entity.AssuranceType;
import preserveother.entity.OrderTicketsInfo;
import preserveother.service.PreserveOtherService;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class PreserveOtherControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    private HttpHeaders headers;
    private String accountId;
    private String contactsId;
    private String tripId;
    private int seatType;
    private Date date;
    private String from;
    private String to;
    private int assurance;
    private int foodType;
    private String stationName;
    private String storeName;
    private String foodName;
    private double foodPrice;
    private String handleDate;
    private String consigneeName;
    private String consigneePhone;
    private double consigneeWeight;
    private boolean isWithin;
    private OrderTicketsInfo oti;

    @Autowired
    PreserveOtherService preserveOtherService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        headers = new HttpHeaders();

//        accountId = "4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f";
        accountId = "f14ccb71c7462a4d6fdad90684b6bbb5";
        contactsId = "222";
        tripId = "333";
        seatType = 1;
        date = new Date(444444);
        from = "shanghai";
        to = "taiyuan";
        // 1
        assurance = AssuranceType.TRAFFIC_ACCIDENT.getIndex();
        foodType = 1;
        stationName = "shanghaistation";
        storeName = "familymart";
        foodName = "bread";
        foodPrice = 6.6;
        handleDate = "2019-11-23";
        consigneeName = "xiaoming";
        consigneePhone = "10086";
        consigneeWeight = 100.0;
        isWithin = true;
        oti = new OrderTicketsInfo(accountId, contactsId, tripId, seatType, date, from, to, assurance,
                foodType, stationName, storeName, foodName, foodPrice, handleDate, consigneeName, consigneePhone, consigneeWeight, isWithin);
    }

    @Test
    public void home() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/preserveotherservice/welcome")
                        .contentType(MediaType.TEXT_PLAIN)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcResultHandlers.print()
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

//    @Test
//    public void preserve() throws Exception {
//        String json = JSONObject.toJSONString(oti);
//        String header_json = JSONObject.toJSONString(headers);
//        mvc.perform(
//                MockMvcRequestBuilders.post("/api/v1/preserveotherservice/preserveother")
//                        .content(json)
//                        .param("headers", header_json)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//        ).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }

//    @MockBean
//    private ConsignService consignService;

//    @Test
//    public void testCreateConsign() throws Exception {
//        doReturn(new Response<>()).when(consignService).insertConsignRecord(new Consign(), new HttpHeaders());
//        preserveOtherService.createConsign(new preserveother.entity.Consign(), new HttpHeaders());
//        assertTrue(true);
//    }

}