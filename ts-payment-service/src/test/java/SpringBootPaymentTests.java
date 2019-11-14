import com.alibaba.fastjson.JSONObject;
import com.trainticket.PaymentApplication;
import com.trainticket.entity.Money;
import com.trainticket.entity.Payment;
import com.trainticket.init.InitData;
import com.trainticket.repository.AddMoneyRepository;
import com.trainticket.repository.PaymentRepository;
import com.trainticket.service.PaymentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Project Name: ts-service
 * Created by HaoqiWu on 10/29/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentApplication.class)
public class SpringBootPaymentTests {
    private String id;
    private String orderId;
    private String userId;
    private String price;
    private String moneyAmount;

    private Payment payment;
    private Money money;
    private MockMvc mvc;

//    private InitData initData;

//    private String header = "content-type:application/json;charset=utf-8";
    private HttpHeaders header;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AddMoneyRepository addMoneyRepository;

    @Autowired
    PaymentService service;

    @Before
    public void setup() {
        id = "123";
        orderId = "2";
        userId = "user1";
        price = "100";
        moneyAmount = "5";

        payment = new Payment();
        payment.setId(id);
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setPrice(price);

        money = new Money();
        money.setUserId(userId);
        money.setMoney(moneyAmount);

//        initData = new InitData();

        mvc = MockMvcBuilders.webAppContextSetup(context)
                //.apply(springSecurity())
                .build();
        header = new HttpHeaders();

        service.query(new HttpHeaders());
    }

    @Test
    public void testEntity() {
        Assert.assertEquals(payment.getId(), id);
        Assert.assertEquals(payment.getOrderId(), orderId);
        Assert.assertEquals(payment.getUserId(), userId);
        Assert.assertEquals(payment.getPrice(), price);

        assert money.getUserId().equals(userId);
        assert money.getMoney().equals(moneyAmount);
    }

    @Test
    public void testPaymentService() throws Exception{
        service.query(new HttpHeaders());

        service.pay(payment, new HttpHeaders());
        paymentRepository.save(payment);
        service.pay(payment, new HttpHeaders());
        paymentRepository.delete(payment);

        service.addMoney(payment, new HttpHeaders());

        service.query(new HttpHeaders());
        paymentRepository.save(payment);
        service.query(new HttpHeaders());

        service.initPayment(payment, new HttpHeaders());
        Payment payment1 = new Payment();
        service.initPayment(payment1, new HttpHeaders());
    }

    @Test
    public void testGetWelcome() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/paymentservice/welcome")
                .contentType(MediaType.TEXT_PLAIN)
        )       .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_PLAIN))
                .andDo(MockMvcResultHandlers.print())////打印出请求和相应的内容
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testPostPayment() throws Exception{
        String json = JSONObject.toJSONString(payment);
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/paymentservice/payment")
                .content(json)//传入参数在这些
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())////打印出请求和相应的内容
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testPostMoney() throws Exception{
        String json = JSONObject.toJSONString(payment);
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/paymentservice/payment/money")
                .content(json)//传入参数在这些
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());////打印出请求和相应的内容
    }

    @Test
    public void testGetPayment() throws Exception{
        String headerJson = JSONObject.toJSONString(header);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/paymentservice/payment")
                .param("headers", headerJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print())////打印出请求和相应的内容
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


//    @Test
//    public void testInitData() throws Exception{
//        initData.run();
//    }

}
