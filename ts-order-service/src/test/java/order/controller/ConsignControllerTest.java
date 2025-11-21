package order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.client.dto.order.ConsignOrderDto;
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
    public void testInsertConsign() throws Exception {
        ConsignOrderDto request = new ConsignOrderDto();
        ConsignOrderDto mappedDto = new ConsignOrderDto();
        ConsignOrder mappedEntity = new ConsignOrder();
        Response<ConsignOrder> serviceResponse = new Response<>(1, "ok", new ConsignOrder());
        Mockito.when(consignMapper.toEntity(Mockito.any(ConsignOrderDto.class))).thenReturn(mappedEntity);
        Mockito.when(service.create(Mockito.eq(mappedEntity), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(consignMapper.toDto(Mockito.any(ConsignOrder.class))).thenReturn(mappedDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/consign-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Response<ConsignOrderDto> actual = objectMapper.readValue(result, new TypeReference<Response<ConsignOrderDto>>() {
        });
        Assertions.assertEquals(serviceResponse.getStatus(), actual.getStatus());
        Assertions.assertEquals(serviceResponse.getMsg(), actual.getMsg());
        Assertions.assertEquals(mappedDto, actual.getData());
    }
}
