package price.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.client.dto.ticket.PriceDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import price.entity.Price;
import price.mapper.PriceMapper;
import price.service.PriceService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class PriceControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private PriceService service;

    @Mock
    private PriceMapper priceMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/prices/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Price Service ] !"));
    }

    @Test
    public void testQuery() throws Exception {
        Response<Price> serviceResponse = new Response<>(1, "Success", new Price());
        Response<PriceDto> dtoResponse = new Response<>(1, "Success", new PriceDto());
        Mockito.when(service.findByRouteIdAndTrainType(Mockito.eq("route_id"), Mockito.eq("train_type"),
                Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(priceMapper.toDtoResponse(serviceResponse)).thenReturn(dtoResponse);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/prices/route_id/train_type"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> actual = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(dtoResponse.getStatus(), actual.getStatus());
        Assert.assertEquals(dtoResponse.getMsg(), actual.getMsg());
        Mockito.verify(priceMapper).toDtoResponse(serviceResponse);
    }

    @Test
    public void testQueryByRouteIdsAndTrainTypes() throws Exception {
        List<String> ridsAndTts = Arrays.asList("routeA:trainA", "routeB:trainB");
        Map<String, Price> priceMap = new HashMap<>();
        priceMap.put("routeA:trainA", new Price());
        Response<Map<String, Price>> serviceResponse = new Response<>(1, "Success", priceMap);
        Response<Map<String, PriceDto>> dtoResponse = new Response<>(1, "Success", new HashMap<>());

        Mockito.when(service.findByRouteIdsAndTrainTypes(Mockito.eq(ridsAndTts), Mockito.any(HttpHeaders.class)))
                .thenReturn(serviceResponse);
        Mockito.when(priceMapper.toDtoMapResponse(serviceResponse)).thenReturn(dtoResponse);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket/prices/byRouteIdsAndTrainTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONObject.toJSONString(ridsAndTts)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> actual = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(dtoResponse.getStatus(), actual.getStatus());
        Assert.assertEquals(dtoResponse.getMsg(), actual.getMsg());
        Mockito.verify(priceMapper).toDtoMapResponse(serviceResponse);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Price> priceList = Collections.singletonList(new Price());
        Response<List<Price>> serviceResponse = new Response<>(1, "Success", priceList);
        Response<List<PriceDto>> dtoResponse = new Response<>(1, "Success", Collections.singletonList(new PriceDto()));

        Mockito.when(service.findAllPrice(Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(priceMapper.toDtoListResponse(serviceResponse)).thenReturn(dtoResponse);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/prices"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Response<?> actual = JSONObject.parseObject(result, Response.class);
        Assert.assertEquals(dtoResponse.getStatus(), actual.getStatus());
        Assert.assertEquals(dtoResponse.getMsg(), actual.getMsg());
        Mockito.verify(priceMapper).toDtoListResponse(serviceResponse);
    }
}
