package other.servicetest;

import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import other.entity.Seat;
import other.repository.OrderOtherRepository;
import other.service.OrderOtherServiceImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderOtherServiceImplTest {

    OrderOtherRepository ordreres;
    OrderOtherServiceImpl ordimp = new OrderOtherServiceImpl();
    RestTemplate restTemplate;

    @BeforeEach
    void setUp() {



    }


    @AfterEach
    void tearDown() {
    }


    @Test
    void getSoldTickets() {
        OrderOtherServiceImpl oimpl = new OrderOtherServiceImpl();
        System.out.println("test");

        Seat seat = new Seat(new Date(123456799),"K1235","shanghai","taiyuan",1);

        Response test = oimpl.getSoldTickets(seat,new HttpHeaders());

        Assert.assertEquals("Seat is Null.",test.getMsg());
    }

    @Test
    void findOrderById() {
    }

    @Test
    void create() {
    }

    @Test
    void initOrder() {
    }

    @Test
    void alterOrder() {
    }

    @Test
    void checkEnableState() {
    }

    @Test
    void checkEnableTravel() {
    }

    @Test
    void checkBoughtState() {
    }

    @Test
    void queryOrders() {
    }

    @Test
    void queryOrdersForRefresh() {
    }

    @Test
    void queryForStationId() {
    }

    @Test
    void saveChanges() {
    }

    @Test
    void cancelOrder() {
    }

    @Test
    void queryAlreadySoldOrders() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void modifyOrder() {
    }

    @Test
    void getOrderPrice() {
    }

    @Test
    void payOrder() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void checkSecurityAboutOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void addNewOrder() {
    }

    @Test
    void updateOrder() {
    }
}