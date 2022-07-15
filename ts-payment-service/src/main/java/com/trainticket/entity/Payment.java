package com.trainticket.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class Payment {
    @Id
    @NotNull
    @Column(length = 36)
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @NotNull
    @Valid
    @Column(length = 36)
    private String orderId;

    @NotNull
    @Valid
    @Column(length = 36)
    private String userId;

    @NotNull
    @Valid
    @Column(name = "payment_price")
    private String price;

    public Payment(){
        this.id = UUID.randomUUID().toString();
        this.orderId = "";
        this.userId = "";
        this.price = "";
    }

}
