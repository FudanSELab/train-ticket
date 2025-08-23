package config.init;

import config.entity.Config;
import config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner{

    @Autowired
    ConfigService service;

    @Override
    public void run(String... args) throws Exception {
        Config config = new Config();

        config.setName("DirectTicketAllocationProportion");
        config.setValue("0.5");
        config.setDescription("Allocation Proportion Of The Direct Ticket - From Start To End");
        service.create(config,null);

        // ts-security-service 风控服务初始化配置
        config.setName("security_max_order_1_hour");
        config.setValue("10");
        config.setDescription("Max order in 1 hour");
        service.create(config,null);

        config.setName("security_max_order_not_use");
        config.setValue("10");
        config.setDescription("Max order not used");
        service.create(config,null);
    }
}
