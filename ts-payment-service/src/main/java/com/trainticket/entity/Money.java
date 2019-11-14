package com.trainticket.entity;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="addMoney")
public class Money {
    private String userId;
    private String moneyAmount;


    public Money(){
        setMoney(String.valueOf(0));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return moneyAmount;
    }

    public void setMoney(String money) {
        this.moneyAmount = money;
    }
}
