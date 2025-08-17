package foodsearch.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Queues {

    public final static String queueName = "food_delivery";

    @Bean
    public Queue emailQueue() {
        return new Queue(queueName);
    }
}
