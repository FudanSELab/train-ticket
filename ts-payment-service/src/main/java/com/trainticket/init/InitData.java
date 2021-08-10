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

package com.trainticket.init;

import com.trainticket.entity.Payment;
import com.trainticket.repository.PaymentRepository;
import com.trainticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    PaymentService service;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public void run(String... args) throws Exception{

        Payment payment = new Payment();
        payment.setId("5ad7750ba68b49c0a8c035276b067701");
        payment.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b067701");
        payment.setPrice("10000.0");
        payment.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        service.initPayment(payment, null);
    }
}

