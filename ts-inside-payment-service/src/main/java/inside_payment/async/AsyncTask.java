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

package inside_payment.async;

import java.util.concurrent.Future;
import inside_payment.entity.OutsidePaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author fdse
 */
@Component  
public class AsyncTask {
    
    @Autowired
	private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTask.class);

    @Async("mySimpleAsync")
    public Future<Boolean> sendAsyncCallToPaymentService(OutsidePaymentInfo outsidePaymentInfo) {
        AsyncTask.LOGGER.info("[Inside Payment Service][Async Task] Begin.");
        Boolean value = restTemplate.getForObject("http://rest-service-external:16100/greet", Boolean.class);
        AsyncTask.LOGGER.info("[Inside Payment Service][Async Task] Receive call Value directly back: {}", value);
        return new AsyncResult<>(value);
    }
    
}  
