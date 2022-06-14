package com.trainticket.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Entity
public class Payment {
    @Id
    @NotNull
    @Valid
    @Column(name = "payment_id")
    private String id;

    @NotNull
    @Valid
    private String orderId;

    @NotNull
    @Valid
    private String userId;

    @NotNull
    @Valid
    @Column(name = "payment_price")
    private String price;

    public Payment(){
        this.id = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        this.orderId = "";
        this.userId = "";
        this.price = "";
    }

}
