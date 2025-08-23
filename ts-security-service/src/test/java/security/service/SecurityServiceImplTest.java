package security.service;

import edu.fudan.common.client.ConfigClient;
import edu.fudan.common.client.OrderClient;
import edu.fudan.common.client.dto.config.ConfigDto;
import edu.fudan.common.client.dto.order.OrderSecurityDto;
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

/**
 * Tests for SecurityServiceImpl.check logic.
 */
@RunWith(JUnit4.class)
public class SecurityServiceImplTest {

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Mock
    private ConfigClient configClient;
    @Mock
    private OrderClient orderClient;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckSuccess() {
        // Order stats within limit
        OrderSecurityDto stats = OrderSecurityDto.builder()
                .orderNumInLastOneHour(1)
                .orderNumOfValidOrder(1)
                .build();
        Response<OrderSecurityDto> orderResp = new Response<>(1, "success", stats);
        Mockito.when(orderClient.getOrderSecurity(Mockito.any(), Mockito.anyString(), Mockito.any())).thenReturn(orderResp);

        // Config thresholds higher than stats
        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_1_hour"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_1_hour", "5", "")));
        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_not_use"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_not_use", "5", "")));

        Response<String> result = securityServiceImpl.check("acc", headers);
        Assert.assertEquals(1, (int) result.getStatus());
    }

    @Test
    public void testCheckExceedHour() {
        OrderSecurityDto stats = OrderSecurityDto.builder()
                .orderNumInLastOneHour(10)
                .orderNumOfValidOrder(1)
                .build();
        Mockito.when(orderClient.getOrderSecurity(Mockito.any(), Mockito.anyString(), Mockito.any()))
                .thenReturn(new Response<>(1, "", stats));

        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_1_hour"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_1_hour", "5", "")));
        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_not_use"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_not_use", "20", "")));

        Response<String> result = securityServiceImpl.check("acc", headers);
        Assert.assertEquals(0, (int) result.getStatus());
    }

    @Test
    public void testCheckExceedNotUsed() {
        OrderSecurityDto stats = OrderSecurityDto.builder()
                .orderNumInLastOneHour(1)
                .orderNumOfValidOrder(30)
                .build();
        Mockito.when(orderClient.getOrderSecurity(Mockito.any(), Mockito.anyString(), Mockito.any()))
                .thenReturn(new Response<>(1, "", stats));

        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_1_hour"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_1_hour", "5", "")));
        Mockito.when(configClient.getConfigByName(Mockito.eq("security_max_order_not_use"), Mockito.any()))
                .thenReturn(new Response<>(1, "", new ConfigDto("security_max_order_not_use", "20", "")));

        Response<String> result = securityServiceImpl.check("acc", headers);
        Assert.assertEquals(0, (int) result.getStatus());
    }
}
