package order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.client.dto.order.OrderQueryDto;
import edu.fudan.common.client.dto.order.SoldOrderStatDto;
import edu.fudan.common.client.dto.order.TravelDateNumberDto;
import edu.fudan.common.entity.OrderSecurity;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import order.entity.Order;
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

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnit4.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void getOrderById_shouldReturnServiceResponse() throws Exception {
        Order order = new Order();
        order.setId("order-id");
        Response<Order> response = new Response<>(1, "Success.", order);
        when(orderService.getOrderById(Mockito.eq("order-id"), Mockito.any(HttpHeaders.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/order-query/orders/{orderId}", "order-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data.id", is("order-id")));

        verify(orderService).getOrderById(Mockito.eq("order-id"), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void queryOrders_shouldDelegateToService() throws Exception {
        OrderQueryDto request = OrderQueryDto.builder().loginId("user-1").build();
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order());
        Response<ArrayList<Order>> response = new Response<>(1, "Get order num", orders);

        when(orderService.queryOrders(Mockito.any(OrderQueryDto.class), Mockito.eq("user-1"), Mockito.any(HttpHeaders.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/order-query/orders/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.msg", is("Get order num")));

        verify(orderService).queryOrders(Mockito.any(OrderQueryDto.class), Mockito.eq("user-1"), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void queryAlreadySoldOrders_shouldReturnAggregatedStats() throws Exception {
        TravelDateNumberDto request = new TravelDateNumberDto();
        request.setTravelDate(StringUtils.Date2String(new Date()));
        request.setTravelNumber("G1234");

        Response<SoldOrderStatDto> response = new Response<>(1, "Success", new SoldOrderStatDto());
        when(orderService.queryAlreadySoldOrders(Mockito.eq(request), Mockito.any(HttpHeaders.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/order-query/orders/get-by-travel-date-number")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));

        verify(orderService).queryAlreadySoldOrders(Mockito.eq(request), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void getOrderPrice_shouldReturnPriceResponse() throws Exception {
        Response<String> response = new Response<>(1, "Success", "88.0");
        when(orderService.getOrderPrice(Mockito.eq("order-id"), Mockito.any(HttpHeaders.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/order-query/orders/{orderId}/price", "order-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)))
                .andExpect(jsonPath("$.data", is("88.0")));

        verify(orderService).getOrderPrice(Mockito.eq("order-id"), Mockito.any(HttpHeaders.class));
    }

    @Test
    public void securityInfoCheck_shouldDelegateToService() throws Exception {
        String checkDate = StringUtils.Date2String(new Date());
        Response<OrderSecurity> response = new Response<>(1, "Success.", new OrderSecurity());
        when(orderService.checkSecurityAboutOrder(Mockito.any(Date.class), Mockito.eq("user-1"), Mockito.any(HttpHeaders.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/order-query/orders/security")
                        .param("checkDate", checkDate)
                        .param("userId", "user-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(1)));

        verify(orderService).checkSecurityAboutOrder(Mockito.any(Date.class), Mockito.eq("user-1"), Mockito.any(HttpHeaders.class));
    }
}

