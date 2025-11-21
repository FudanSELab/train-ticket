package order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.client.dto.order.FoodOrderDto;
import edu.fudan.common.util.Response;
import order.entity.FoodOrder;
import order.mapper.FoodOrderMapper;
import order.service.FoodOrderService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class FoodControllerTest {

    @InjectMocks
    private FoodOrderController foodController;

    @Mock
    private FoodOrderService foodService;

    @Mock
    private FoodOrderMapper foodOrderMapper;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
    }

    @Test
    public void findAllFoodOrder_shouldReturnMappedDtos() throws Exception {
        List<FoodOrder> entities = Collections.singletonList(new FoodOrder());
        List<FoodOrderDto> dtos = Collections.singletonList(new FoodOrderDto());
        Response<List<FoodOrder>> response = new Response<>(1, "Success", entities);
        Mockito.when(foodService.findAllFoodOrder(Mockito.any(HttpHeaders.class))).thenReturn(response);
        Mockito.when(foodOrderMapper.toDtoList(entities)).thenReturn(dtos);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/food-orders/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response<List<FoodOrderDto>> actual = objectMapper.readValue(result,
                new TypeReference<Response<List<FoodOrderDto>>>() {
                });
        Assertions.assertEquals(response.getStatus(), actual.getStatus());
        Assertions.assertEquals(response.getMsg(), actual.getMsg());
        Assertions.assertEquals(dtos, actual.getData());
    }

    @Test
    public void findFoodOrderByOrderId_shouldReturnDto() throws Exception {
        FoodOrder entity = new FoodOrder();
        FoodOrderDto dto = new FoodOrderDto();
        Response<FoodOrder> serviceResponse = new Response<>(1, "Success.", entity);
        Mockito.when(foodService.findByOrderId(Mockito.anyString(), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(foodOrderMapper.toDto(entity)).thenReturn(dto);

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/food-orders/{orderId}", "order_id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response<FoodOrderDto> actual = objectMapper.readValue(result, new TypeReference<Response<FoodOrderDto>>() {
        });
        Assertions.assertEquals(serviceResponse.getStatus(), actual.getStatus());
        Assertions.assertEquals(serviceResponse.getMsg(), actual.getMsg());
        Assertions.assertEquals(dto, actual.getData());
    }

}


