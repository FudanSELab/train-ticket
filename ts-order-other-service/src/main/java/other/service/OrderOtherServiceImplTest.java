package other.service;

import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import other.entity.Seat;

import javax.naming.directory.SearchControls;


import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderOtherServiceImplTest {

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

        OrderOtherServiceImpl oimpl = new OrderOtherServiceImpl();
        System.out.println("test");
        Calendar cld = Calendar.getInstance();
        cld.set(2014,11,2,1,1,1);
        Date set = cld.getTime();
        Seat seat = new Seat(set,"test1","shenzhen","shanghai",1);
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

    @Test
    void testGetSoldTickets() {
    }

    @Test
    void testFindOrderById() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testInitOrder() {
    }

    @Test
    void testAlterOrder() {
    }

    @Test
    void testCheckEnableState() {
    }

    @Test
    void testCheckEnableTravel() {
    }

    @Test
    void testCheckBoughtState() {
    }

    @Test
    void testQueryOrders() {
    }

    @Test
    void testQueryOrdersForRefresh() {
    }

    @Test
    void testQueryForStationId() {
    }

    @Test
    void testSaveChanges() {
    }

    @Test
    void testCancelOrder() {
    }

    @Test
    void testQueryAlreadySoldOrders() {
    }

    @Test
    void testGetAllOrders() {
    }

    @Test
    void testModifyOrder() {
    }

    @Test
    void testGetOrderPrice() {
    }

    @Test
    void testPayOrder() {
    }

    @Test
    void testGetOrderById() {
    }

    @Test
    void testCheckSecurityAboutOrder() {
    }

    @Test
    void testDeleteOrder() {
    }

    @Test
    void testAddNewOrder() {
    }

    @Test
    void testUpdateOrder() {
    }
}