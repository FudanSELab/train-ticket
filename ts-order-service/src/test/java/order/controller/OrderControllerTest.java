package order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.client.dto.order.ModifyOrderStatusDto;
import edu.fudan.common.client.dto.order.OrderDto;
import edu.fudan.common.util.Response;
import order.entity.Order;
import order.mapper.ModifyOrderStatusMapper;
import order.mapper.OrderMapper;
import order.service.OrderService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnit4.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ModifyOrderStatusMapper modifyOrderStatusMapper;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void createNewOrder_shouldReturnServiceResponse() throws Exception {
        OrderDto requestDto = OrderDto.builder()
                .id("order-id")
                .userId("user-1")
                .fromStationId("shanghai")
                .toStationId("beijing")
                .build();

        Order orderEntity = new Order();
        orderEntity.setId("order-id");

        Response<Order> serviceResponse = new Response<>(1, "Success", orderEntity);

        when(orderMapper.toEntity(Mockito.any(OrderDto.class))).thenReturn(orderEntity);
        when(orderService.create(Mockito.eq(orderEntity), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        when(orderMapper.toDto(orderEntity)).thenReturn(requestDto);

        mockMvc.perform(post("/api/v1/order/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id", is("order-id")));

        verify(orderService).create(Mockito.eq(orderEntity), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void saveOrderInfo_shouldUpdateOrder() throws Exception {
        OrderDto requestDto = OrderDto.builder()
                .id("order-id")
                .userId("user-1")
                .fromStationId("shanghai")
                .toStationId("beijing")
                .build();

        Order orderEntity = new Order();
        orderEntity.setId("order-id");

        Response<Order> serviceResponse = new Response<>(1, "Success", orderEntity);

        when(orderMapper.toEntity(Mockito.any(OrderDto.class))).thenReturn(orderEntity);
        when(orderService.update(Mockito.eq(orderEntity), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        when(orderMapper.toDto(orderEntity)).thenReturn(requestDto);

        mockMvc.perform(put("/api/v1/order/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id", is("order-id")));

        verify(orderService).update(Mockito.eq(orderEntity), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void payOrder_shouldDelegateToService() throws Exception {
        String orderId = "order-id";
        Order orderEntity = new Order();
        orderEntity.setId(orderId);
        OrderDto responseDto = OrderDto.builder().id(orderId).build();
        Response<Order> serviceResponse = new Response<>(1, "Pay Order Success.", orderEntity);

        when(orderService.pay(Mockito.eq(orderId), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        when(orderMapper.toDto(orderEntity)).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/order/orders/{orderId}/pay", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id", is(orderId)))
                .andExpect(jsonPath("$.msg", is("Pay Order Success.")));

        verify(orderService).pay(Mockito.eq(orderId), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void modifyOrder_shouldMapStatusAndDelegate() throws Exception {
        String orderId = "order-id";
        ModifyOrderStatusDto request = ModifyOrderStatusDto.builder().status(2).build();
        Order orderEntity = new Order();
        orderEntity.setId(orderId);
        OrderDto responseDto = OrderDto.builder().id(orderId).status(2).build();
        Response<Order> serviceResponse = new Response<>(1, "Modify Order Success", orderEntity);

        when(modifyOrderStatusMapper.toStatus(Mockito.any(ModifyOrderStatusDto.class))).thenReturn(2);
        when(orderService.updateStatus(Mockito.eq(orderId), Mockito.eq(2), Mockito.any(HttpHeaders.class))).thenReturn(serviceResponse);
        when(orderMapper.toDto(orderEntity)).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/order/orders/{orderId}/status", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.status", is(2)));

        verify(orderService).updateStatus(Mockito.eq(orderId), Mockito.eq(2), Mockito.any(HttpHeaders.class));
    }
}


