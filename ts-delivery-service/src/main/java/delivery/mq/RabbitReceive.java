/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
