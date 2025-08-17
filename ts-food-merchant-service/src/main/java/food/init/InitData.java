package food.init;

import food.service.FoodMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner{

    @Autowired
    FoodMerchantService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(InitData.class);


    @Override
    public void run(String... args) throws Exception {
    }
}
