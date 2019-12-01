package Test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import train.service.TrainServiceImpl;

@Configuration
public class TestConfig{
    @Bean
    public TrainServiceImpl trainServiceImpl() {
        TrainServiceImpl trainServiceImpl = new TrainServiceImpl();
        return trainServiceImpl;
    }
}
