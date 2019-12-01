package train.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import train.entity.TrainType;
import train.service.TrainService;


@Component
public class InitData implements CommandLineRunner {

    @Autowired
    TrainService service;

    void serInfoCreate(TrainType info, String id, int confortClass, int economyClass, int averageSpeed){
        info.setId(id);
        info.setConfortClass(confortClass);
        info.setEconomyClass(economyClass);
        info.setAverageSpeed(averageSpeed);
        service.create(info, null);
    }

    @Override
    public void run(String... args) throws Exception {
        TrainType info = new TrainType();
    
        serInfoCreate(info, "GaoTieOne", Integer.MAX_VALUE, Integer.MAX_VALUE, 250);
        serInfoCreate(info, "GaoTieTwo", Integer.MAX_VALUE, Integer.MAX_VALUE, 200);
        serInfoCreate(info, "DongCheOne", Integer.MAX_VALUE, Integer.MAX_VALUE, 180);
        serInfoCreate(info, "ZhiDa", Integer.MAX_VALUE, Integer.MAX_VALUE, 120);
        serInfoCreate(info, "TeKuai", Integer.MAX_VALUE, Integer.MAX_VALUE, 120);
        serInfoCreate(info, "KuaiSu", Integer.MAX_VALUE, Integer.MAX_VALUE, 90);
    }
}
