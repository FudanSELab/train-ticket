package fdse.microservice.init;

import fdse.microservice.entity.Station;
import fdse.microservice.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class InitData implements CommandLineRunner {

    @Autowired
    StationService service;

    @Override
    public void run(String... args) throws Exception{
        Station info = new Station();

        String[] IDList={"shanghai","shanghaihongqiao","taiyuan","beijing","nanjing","shijiazhuang","xuzhou","jinan","hangzhou","jiaxingnan","zhenjiang","wuxi","suzhou"};
        String[] NameList={"Shang Hai","Shang Hai Hong Qiao","Tai Yuan","Bei Jing","Nan Jing","Shi Jia Zhuang","Xu Zhou","Ji Nan","Hang Zhou","Jia Xing Nan","Zhen Jiang","Wu Xi","Su Zhou"};
        int[] STList={10,10,5,10,8,8,7,5,9,2,2,3,3};
        for(int i=0;i<13;i++){
            info.setId(IDList[i]);
            info.setName(NameList[i]);
            info.setStayTime(STList[i]);
            service.create(info,null);
        }



    }
}
