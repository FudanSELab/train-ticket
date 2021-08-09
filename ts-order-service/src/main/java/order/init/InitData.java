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

package order.init;

import order.entity.Order;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderService service;

    String accountId = "4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f";
    String contactName = "Contacts_One";
    String contactDocumentNumber = "DocumentNumber_One";
    String firstClass = "FirstClass-30";
    String price = "100.0";

    @Override
    public void run(String... args)throws Exception{
        Order order = new Order();
        order.setId(UUID.fromString("5ad7750b-a68b-49c0-a8c0-32776b067703"));
        order.setBoughtDate(new Date());
        order.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017")); //NOSONAR
        order.setTravelTime(new Date("Mon May 04 09:02:00 GMT+0800 2013")); //NOSONAR
        order.setAccountId(UUID.fromString(accountId));
        order.setContactsName(contactName);
        order.setDocumentType(1);
        order.setContactsDocumentNumber(contactDocumentNumber);
        order.setTrainNumber("G1237");
        order.setCoachNumber(5);
        order.setSeatClass(2);
        order.setSeatNumber(firstClass);
        order.setFrom("nanjing");
        order.setTo("shanghaihongqiao");
        order.setStatus(0);
        order.setPrice(price);
        service.initOrder(order, null);


        Order orderTwo = new Order();
        orderTwo.setId(UUID.fromString("8177ac5a-61ac-42f4-83f4-bd7b394d0531"));
        orderTwo.setBoughtDate(new Date());
        orderTwo.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017")); //NOSONAR
        orderTwo.setTravelTime(new Date("Mon May 04 09:01:00 GMT+0800 2013")); //NOSONAR
        orderTwo.setAccountId(UUID.fromString(accountId));
        orderTwo.setContactsName(contactName);
        orderTwo.setDocumentType(1);
        orderTwo.setContactsDocumentNumber(contactDocumentNumber);
        orderTwo.setTrainNumber("G1234");
        orderTwo.setCoachNumber(5);
        orderTwo.setSeatClass(2);
        orderTwo.setSeatNumber(firstClass);
        orderTwo.setFrom("shanghai");
        orderTwo.setTo("beijing");
        orderTwo.setStatus(0);
        orderTwo.setPrice(price);
        service.initOrder(orderTwo, null);

        Order orderThree = new Order();
        orderThree.setId(UUID.fromString("d3c91694-d5b8-424c-9974-e14c89226e49"));
        orderThree.setBoughtDate(new Date());
        orderThree.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017")); //NOSONAR
        orderThree.setTravelTime(new Date("Mon May 04 09:00:00 GMT+0800 2013")); //NOSONAR
        orderThree.setAccountId(UUID.fromString(accountId));
        orderThree.setContactsName(contactName);
        orderThree.setDocumentType(1);
        orderThree.setContactsDocumentNumber(contactDocumentNumber);
        orderThree.setTrainNumber("G1235");
        orderThree.setCoachNumber(5);
        orderThree.setSeatClass(2);
        orderThree.setSeatNumber(firstClass);
        orderThree.setFrom("shanghai");
        orderThree.setTo("beijing");
        orderThree.setStatus(0);
        orderThree.setPrice(price);
        service.initOrder(orderThree, null);
    }

}
