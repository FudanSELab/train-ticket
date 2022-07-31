package order.init;

import edu.fudan.common.util.StringUtils;
import order.entity.Order;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderService service;

    String accountId = "4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f";
    String contactName = "Contacts_One";
    String contactDocumentNumber = "DocumentNumber_One";
    String firstClass = "FirstClass-30";
    String price = "100.0";

    @Override
    public void run(String... args)throws Exception{
        Order order = new Order();
        order.setId("5ad7750b-a68b-49c0-a8c0-32776b067703");
        order.setTravelDate("2022-10-01 00:00:00"); //NOSONAR
        order.setTravelTime("2022-10-01 00:00:00"); //NOSONAR
        order.setAccountId(accountId);
        order.setContactsName(contactName);
        order.setDocumentType(1);
        order.setContactsDocumentNumber(contactDocumentNumber);
        order.setTrainNumber("G1237");
        order.setCoachNumber(5);
        order.setSeatClass(2);
        order.setSeatNumber(firstClass);
        order.setFrom("nanjing");
        order.setTo("shanghaihongqiao");
        order.setStatus(0);
        order.setPrice(price);
        service.initOrder(order, null);


        Order orderTwo = new Order();
        orderTwo.setId("8177ac5a-61ac-42f4-83f4-bd7b394d0531");
        orderTwo.setTravelDate("2022-10-01 00:00:00"); //NOSONAR
        orderTwo.setTravelTime("2022-10-01 00:00:00"); //NOSONAR
        orderTwo.setAccountId(accountId);
        orderTwo.setContactsName(contactName);
        orderTwo.setDocumentType(1);
        orderTwo.setContactsDocumentNumber(contactDocumentNumber);
        orderTwo.setTrainNumber("G1234");
        orderTwo.setCoachNumber(5);
        orderTwo.setSeatClass(2);
        orderTwo.setSeatNumber(firstClass);
        orderTwo.setFrom("shanghai");
        orderTwo.setTo("beijing");
        orderTwo.setStatus(0);
        orderTwo.setPrice(price);
        service.initOrder(orderTwo, null);

        Order orderThree = new Order();
        orderThree.setId("d3c91694-d5b8-424c-9974-e14c89226e49");
        orderThree.setTravelDate("2022-10-01 00:00:00"); //NOSONAR
        orderThree.setTravelTime("2022-10-01 00:00:00"); //NOSONAR
        orderThree.setAccountId(accountId);
        orderThree.setContactsName(contactName);
        orderThree.setDocumentType(1);
        orderThree.setContactsDocumentNumber(contactDocumentNumber);
        orderThree.setTrainNumber("G1235");
        orderThree.setCoachNumber(5);
        orderThree.setSeatClass(2);
        orderThree.setSeatNumber(firstClass);
        orderThree.setFrom("shanghai");
        orderThree.setTo("beijing");
        orderThree.setStatus(0);
        orderThree.setPrice(price);
        service.initOrder(orderThree, null);
    }

}
