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

    @Override
    public void run(String... args) throws Exception {
        TrainType info = new TrainType();

        info.setName("GaoTieOne");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(250);
        service.create(info, null);

        info = new TrainType();
        info.setName("GaoTieTwo");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(200);
        service.create(info, null);

        info = new TrainType();
        info.setName("DongCheOne");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(180);
        service.create(info, null);

        info = new TrainType();
        info.setName("ZhiDa");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(120);
        service.create(info, null);

        info = new TrainType();
        info.setName("TeKuai");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(120);
        service.create(info, null);

        info = new TrainType();
        info.setName("KuaiSu");
        info.setConfortClass(Integer.MAX_VALUE);
        info.setEconomyClass(Integer.MAX_VALUE);
        info.setAverageSpeed(90);
        service.create(info, null);
    }
}
