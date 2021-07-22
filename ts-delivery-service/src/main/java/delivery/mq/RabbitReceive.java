package delivery.mq;

import delivery.config.Queues;
import delivery.entity.Delivery;
import delivery.repository.DeliveryRepository;
import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Component
public class RabbitReceive {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReceive.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeliveryRepository deliveryRepository;


    @RabbitListener(queues = Queues.queueName)
    public void process(String payload) {
        Delivery delivery = JsonUtils.json2Object(payload, Delivery.class);

        if (delivery == null) {
            logger.error("Receive delivery object is null, error.");
            return;
        }
        logger.info("Receive delivery object:" + delivery);

        if (delivery.getId() == null) {
            delivery.setId(UUID.randomUUID());
        }

        try {
            deliveryRepository.save(delivery);
            logger.info("Save delivery object into database success");
        } catch (Exception e) {
            logger.error("Save delivery object into database failed, exception [{}]", e.toString());
        }
    }
}
