package order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.client.dto.order.ConsignOrderDto;
import edu.fudan.common.client.dto.order.GetConsignOrdersByConsigneeDto;
import edu.fudan.common.client.dto.order.GetConsignOrdersByUserIdDto;
import edu.fudan.common.util.Response;
import order.entity.ConsignOrder;
import order.mapper.ConsignOrderMapper;
import order.service.ConsignOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.jupiter.api.Assertions;
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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ConsignControllerTest {

    @InjectMocks
    private ConsignOrderController consignController;

    @Mock
    private ConsignOrderService service;

    @Mock
    private ConsignOrderMapper consignMapper;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consignController).build();
    }

    @Test
    public void testFindByUserId() throws Exception {
        UUID id = UUID.randomUUID();
        List<ConsignOrder> records = Collections.singletonList(new ConsignOrder());
        Response<List<ConsignOrder>> response = new Response<>(1, "ok", records);
        List<ConsignOrderDto> mapped = Collections.singletonList(new ConsignOrderDto());
        Mockito.when(service.getByUserId(Mockito.any(UUID.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        Mockito.when(consignMapper.toDtoList(records)).thenReturn(mapped);
        GetConsignOrdersByUserIdDto request = new GetConsignOrdersByUserIdDto();
        request.setUserId(id.toString());
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/consign-orders/get-by-user-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Response<List<ConsignOrderDto>> actual = objectMapper.readValue(result,
                new TypeReference<Response<List<ConsignOrderDto>>>() {
                });
        Assertions.assertEquals(response.getStatus(), actual.getStatus());
        Assertions.assertEquals(response.getMsg(), actual.getMsg());
        Assertions.assertEquals(mapped, actual.getData());
    }

    @Test
    public void testFindByOrderId() throws Exception {
        UUID id = UUID.randomUUID();
        ConsignOrder record = new ConsignOrder();
        ConsignOrderDto mappedDto = new ConsignOrderDto();
        Response<ConsignOrder> response = new Response<>(1, "ok", record);
        Mockito.when(service.getByOrderId(Mockito.any(UUID.class), Mockito.any(HttpHeaders.class))).thenReturn(response);
        Mockito.when(consignMapper.toDto(record)).thenReturn(mappedDto);
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/consign-orders/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Response<ConsignOrderDto> actual = objectMapper.readValue(result, new TypeReference<Response<ConsignOrderDto>>() {
        });
        Assertions.assertEquals(response.getStatus(), actual.getStatus());
        Assertions.assertEquals(response.getMsg(), actual.getMsg());
        Assertions.assertEquals(mappedDto, actual.getData());
    }

    @Test
    public void testFindByConsignee() throws Exception {
        List<ConsignOrder> records = Collections.singletonList(new ConsignOrder());
        List<ConsignOrderDto> mapped = Collections.singletonList(new ConsignOrderDto());
        Response<List<ConsignOrder>> response = new Response<>(1, "ok", records);
        Mockito.when(service.getByConsignee(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(response);
        Mockito.when(consignMapper.toDtoList(records)).thenReturn(mapped);
        GetConsignOrdersByConsigneeDto request = new GetConsignOrdersByConsigneeDto();
        request.setConsignee("test-consignee");
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/consign-orders/get-by-consignee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Response<List<ConsignOrderDto>> actual = objectMapper.readValue(result,
                new TypeReference<Response<List<ConsignOrderDto>>>() {
                });
        Assertions.assertEquals(response.getStatus(), actual.getStatus());
        Assertions.assertEquals(response.getMsg(), actual.getMsg());
        Assertions.assertEquals(mapped, actual.getData());
    }
}
