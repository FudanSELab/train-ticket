package com.trainticket.init;

import com.trainticket.entity.Payment;
import com.trainticket.repository.PaymentRepository;
import com.trainticket.service.PaymentService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

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

        /*Payment payment_1 = new Payment();
        payment_1.setId("5ad7750ba68b49c0a8c035276b060000");
        payment_1.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b060000");
        payment_1.setPrice("10000.0");
        payment_1.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d90000");
        service.initPayment(payment_1, null);  //findById

        Payment payment_2 = new Payment();
        payment_2.setId("5ad7750ba68b49c0a8c035276b061111");
        payment_2.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b061111");
        payment_2.setPrice("5000.0");
        payment_2.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d91111");
        service.initPayment(payment_2, null);

        Response r1 = service.query(null);  //findAll
        List<Payment> payments = (List<Payment>) r1.getData();
        System.out.println(payments.size());

        Payment payment_3 = new Payment();
        payment_3.setId("5ad7750ba68b49c0a8c035276b062222");
        payment_3.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b062222");
        payment_3.setPrice("500.0");
        payment_3.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d92222");
        service.pay(payment_3,null);  //findByOrderID

        service.addMoney(payment_1,null); //addMoney
        service.addMoney(payment_2,null); //addMoney*/

    }
}

