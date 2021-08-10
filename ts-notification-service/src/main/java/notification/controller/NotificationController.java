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

package notification.controller;

import notification.entity.NotifyInfo;
import notification.mq.RabbitSend;
import notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wenvi
 * @date 2017/6/15
 */
@RestController
@RequestMapping("/api/v1/notifyservice")
public class NotificationController {

    @Autowired
    NotificationService service;

    @Autowired
    RabbitSend sender;

    @Value("${test_send_mail_user}")
    String test_mail_user;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Notification Service ] !";
    }

    @GetMapping("/test_send_mq")
    public boolean test_send() {
        sender.send("test");
        return true;
    }

    @GetMapping("/test_send_mail")
    public boolean test_send_mail() {
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setDate("Wed Jul 21 09:49:44 CST 2021");
        notifyInfo.setEmail(test_mail_user);
        notifyInfo.setEndPlace("Test");
        notifyInfo.setStartingPlace("Test");
        notifyInfo.setOrderNumber("111-111-111");
        notifyInfo.setPrice("100");
        notifyInfo.setSeatClass("1");
        notifyInfo.setSeatNumber("1102");
        notifyInfo.setStartingTime("Sat May 04 07:00:00 CST 2013");
        notifyInfo.setUsername("h10g");

        service.preserveSuccess(notifyInfo, null);
        return true;
    }

    @PostMapping(value = "/notification/preserve_success")
    public boolean preserve_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.preserveSuccess(info, headers);
    }

    @PostMapping(value = "/notification/order_create_success")
    public boolean order_create_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.orderCreateSuccess(info, headers);
    }

    @PostMapping(value = "/notification/order_changed_success")
    public boolean order_changed_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.orderChangedSuccess(info, headers);
    }

    @PostMapping(value = "/notification/order_cancel_success")
    public boolean order_cancel_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.orderCancelSuccess(info, headers);
    }
}
