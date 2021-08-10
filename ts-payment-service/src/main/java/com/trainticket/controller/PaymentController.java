package com.trainticket.controller;

import com.trainticket.entity.Payment;
import com.trainticket.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Chenjie
 * @date 2017/4/7
 */
@RestController
@RequestMapping("/api/v1/paymentservice")
public class PaymentController {

    @Autowired
    PaymentService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Payment Service ] !";
    }

    @PostMapping(path = "/payment")
    public HttpEntity pay(@RequestBody Payment info, @RequestHeader HttpHeaders headers) {
        PaymentController.LOGGER.info("Pay, PaymentId: {}", info.getId());
        return ok(service.pay(info, headers));
    }

    @PostMapping(path = "/payment/money")
    public HttpEntity addMoney(@RequestBody Payment info, @RequestHeader HttpHeaders headers) {
        PaymentController.LOGGER.info("Add money, PaymentId: {}", info.getId());
        return ok(service.addMoney(info, headers));
    }

    @GetMapping(path = "/payment")
    public HttpEntity query(@RequestHeader HttpHeaders headers) {
        PaymentController.LOGGER.info("Query payment");
        return ok(service.query(headers));
    }
}
