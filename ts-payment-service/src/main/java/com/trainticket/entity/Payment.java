package com.trainticket.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Document(collection="payment")
public class Payment {
    @Id
    @NotNull
    @Valid
    private String id;

    @NotNull
    @Valid
    private String orderId;

    @NotNull
    @Valid
    private String userId;

    @NotNull
    @Valid
    private String price;

    public Payment(){
        this.id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        this.orderId = "0";
        this.userId = "null";
        this.price = "5";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
