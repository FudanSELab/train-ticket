/**
 * @author shenxinyao
 * @date 2019/11/19
 */
package other.init;

import other.entity.*;
import other.service.OrderOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderOtherService service;

    @Override
    public void run(String... args)throws Exception{

        Order order1 = new Order();

        order1.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        order1.setBoughtDate(new Date(123456799));
        order1.setCoachNumber(5);
        order1.setContactsDocumentNumber("Test");
        order1.setContactsName("Test");
        order1.setDocumentType(1);
        order1.setFrom("shanghai");
        order1.setId(UUID.fromString("4d2a46c7-71cb-4cf1-c5bb-b68406d9da6f"));
        order1.setPrice("100");
        order1.setSeatClass(SeatClass.FIRSTCLASS.getCode());
        order1.setSeatNumber("6A");
        order1.setStatus(OrderStatus.PAID.getCode());
        order1.setTo("taiyuan");
        order1.setTrainNumber("K1235");
        order1.setTravelDate(new Date(123456799));
        order1.setTravelTime(new Date(123456799));
        service.create(order1,null);
    }

}
