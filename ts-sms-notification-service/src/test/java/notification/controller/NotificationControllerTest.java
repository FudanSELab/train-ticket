package notification.controller;

import com.alibaba.fastjson.JSONObject;
import edu.fudan.common.util.Response;
import notification.entity.NotifyInfo;
import notification.service.NotificationService;
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

@RunWith(JUnit4.class)
public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService service;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user-notification/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to [ Notification Service ] !"));
    }

    @Test
    public void testPreserveSuccess() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.preserveSuccess(Mockito.any(NotifyInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-notification/notification/preserve_success").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(JSONObject.parseObject(result, Boolean.class));
    }

    @Test
    public void testOrderCreateSuccess() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderCreateSuccess(Mockito.any(NotifyInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-notification/notification/order_create_success").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(JSONObject.parseObject(result, Boolean.class));
    }

    @Test
    public void testOrderChangedSuccess() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderChangedSuccess(Mockito.any(NotifyInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-notification/notification/order_changed_success").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(JSONObject.parseObject(result, Boolean.class));
    }

    @Test
    public void testOrderCancelSuccess() throws Exception {
        NotifyInfo info = new NotifyInfo();
        Mockito.when(service.orderCancelSuccess(Mockito.any(NotifyInfo.class), Mockito.any(HttpHeaders.class))).thenReturn(true);
        String requestJson = JSONObject.toJSONString(info);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user-notification/notification/order_cancel_success").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(JSONObject.parseObject(result, Boolean.class));
    }

}
