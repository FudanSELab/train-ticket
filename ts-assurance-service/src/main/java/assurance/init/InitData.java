package assurance.init;

import assurance.entity.Assurance;
import assurance.entity.AssuranceType;
import assurance.entity.AssuranceTypeBean;
import assurance.entity.PlainAssurance;
import assurance.repository.AssuranceRepository;
import assurance.service.AssuranceService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    AssuranceService service;

    @Autowired
    AssuranceRepository repository;

    @Override
    public void run(String... args) throws Exception {
        //do nothing

       /* Assurance assurance1=new Assurance();
        String id="ff8080817b3e4c27017b3e4c3bee0000";
        assurance1.setId(id);
        assurance1.setType(AssuranceType.TRAFFIC_ACCIDENT);
        repository.save(assurance1);

        Assurance res1=repository.findById(id).get();
        System.out.println("ID: " + id + "  orderID: " + res1.getOrderId());  //根据ID查找 ， 打印orderID
        Assurance res2=repository.findByOrderId(res1.getOrderId());
        System.out.println("ID: " + res2.getId() + "  orderID: " + res1.getOrderId());  //根据orderID查找 ， 打印ID

        Assurance assurance2=new Assurance();
        String id2="ff8080817b3e4c27017b3e4c3bee1111";
        assurance2.setId(id2);
        assurance2.setType(AssuranceType.TRAFFIC_ACCIDENT);
        repository.save(assurance2);

        ArrayList<Assurance> res3 = repository.findAll();  //查找所有
        System.out.println("num: " + res3.size());

        repository.deleteById(res3.get(0).getId());  //用ID删除第一个
        System.out.println("delete successfully");
        repository.removeAssuranceByOrderId(res3.get(1).getOrderId());   //用orderID删除第二个
        System.out.println("delete successfully");

        //测试service
        String id_1 = UUID.randomUUID().toString();
        Response r_1 = service.create(1,id_1,null);  //create
        Assurance assurance_1 = (Assurance)r_1.getData();
        String id_2 = UUID.randomUUID().toString();
        Response r_2 = service.create(1,id_2,null);
        Assurance assurance_2 = (Assurance)r_2.getData();

        service.findAssuranceById(UUID.fromString(id_1),null);   //findAssuranceById
        service.findAssuranceByOrderId(UUID.fromString(assurance_2.getOrderId()),null); //findAssuranceByOrderId
        Response r_3 = service.getAllAssurances(null);
        ArrayList<PlainAssurance> data_1 = (ArrayList<PlainAssurance>)r_3.getData();
        System.out.println(data_1.size());
        Response r_4 = service.getAllAssuranceTypes(null);
        List<AssuranceTypeBean> data_2 = (List<AssuranceTypeBean>)r_4.getData();
        System.out.println(data_2.size());

        service.modify(assurance_2.getId(),assurance_2.getOrderId(),2,null);  //modify实际上2不存在日志中error

        service.deleteById(UUID.fromString(assurance_1.getId()),null);   //deleteById
        service.deleteByOrderId(UUID.fromString(assurance_2.getOrderId()),null);  //deleteByOrderId
*/
    }
}
