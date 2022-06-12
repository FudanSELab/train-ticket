package consign.init;

import consign.entity.Consign;
import consign.entity.ConsignRecord;
import consign.repository.ConsignRepository;
import consign.service.ConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    ConsignService service;

    @Autowired
    ConsignRepository repository;

    @Override
    public void run(String... args) throws Exception {
        //do nothing
       /* ConsignRecord c_1 = new ConsignRecord();
        String id_1 = "ff8080817b3e4c27017b3e4c3bee0000";
        String orderID_1 = "ff8080817b3e4c27017b3e4order0000";
        String accountID_1 = "ff8080817b3e4c27017b3e4c3acc0000";
        String consignee_1 = "xxx1";
        c_1.setId(id_1);
        c_1.setAccountId(accountID_1);
        c_1.setOrderId(orderID_1);
        c_1.setConsignee(consignee_1);
        c_1.setFrom("from");
        c_1.setTo("to");
        c_1.setHandleDate("handle_date");
        c_1.setTargetDate("target_date");
        c_1.setPhone("12345");
        c_1.setWeight(10);
        repository.save(c_1);

        ConsignRecord c_2 = new ConsignRecord();
        String id_2 = "ff8080817b3e4c27017b3e4c3bee1111";
        String orderID_2 = "ff8080817b3e4c27017b3e4order1111";
        String accountID_2 = "ff8080817b3e4c27017b3e4c3acc0000";  //同一个account
        String consignee_2 = "xxx2";
        c_2.setId(id_2);
        c_2.setAccountId(accountID_2);
        c_2.setOrderId(orderID_2);
        c_2.setConsignee(consignee_2);
        c_2.setFrom("from2");
        c_2.setTo("to2");
        c_2.setHandleDate("handle_date2");
        c_2.setTargetDate("target_date2");
        c_2.setPhone("12345");
        c_2.setWeight(12);
        repository.save(c_2);

        ConsignRecord res1 = repository.findById(id_1).get();
        System.out.println("id查找成功 ： " + res1.getId());
        ConsignRecord res2 = repository.findByOrderId(orderID_1);
        System.out.println("orderID查找成功 ： "+ res2.getId());
        ArrayList<ConsignRecord> res3 = repository.findByAccountId(accountID_1);
        System.out.println("accountID查找成功 ： " + res3.size());
        ArrayList<ConsignRecord> res4 = repository.findByConsignee(consignee_1);
        System.out.println("consignee查找成功 ： " + res4.size());
*/


    }





}
