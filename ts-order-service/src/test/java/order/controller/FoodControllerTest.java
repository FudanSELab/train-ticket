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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public void createFoodOrder_shouldDelegateToService() throws Exception {
        FoodOrderDto request = new FoodOrderDto();
        FoodOrder entity = new FoodOrder();
        Response<FoodOrder> serviceResponse = new Response<>(1, "Created", entity);
        Mockito.when(foodOrderMapper.toEntity(Mockito.any(FoodOrderDto.class))).thenReturn(entity);
        Mockito.when(foodService.createFoodOrder(Mockito.eq(entity), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(foodOrderMapper.toDto(entity)).thenReturn(request);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/food-orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response<FoodOrderDto> actual = objectMapper.readValue(result, new TypeReference<Response<FoodOrderDto>>() {
        });
        Assertions.assertEquals(serviceResponse.getStatus(), actual.getStatus());
        Assertions.assertEquals(serviceResponse.getMsg(), actual.getMsg());
        Assertions.assertEquals(request, actual.getData());
    }

    @Test
    public void updateFoodOrder_shouldReturnUpdatedDto() throws Exception {
        FoodOrderDto request = new FoodOrderDto();
        FoodOrder entity = new FoodOrder();
        Response<FoodOrder> serviceResponse = new Response<>(1, "Updated", entity);
        Mockito.when(foodOrderMapper.toEntity(Mockito.any(FoodOrderDto.class))).thenReturn(entity);
        Mockito.when(foodService.updateFoodOrder(Mockito.eq(entity), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        Mockito.when(foodOrderMapper.toDto(entity)).thenReturn(request);

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order/food-orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response<FoodOrderDto> actual = objectMapper.readValue(result, new TypeReference<Response<FoodOrderDto>>() {
        });
        Assertions.assertEquals(serviceResponse.getStatus(), actual.getStatus());
        Assertions.assertEquals(serviceResponse.getMsg(), actual.getMsg());
        Assertions.assertEquals(request, actual.getData());
    }
}


