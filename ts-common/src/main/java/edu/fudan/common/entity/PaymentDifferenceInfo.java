package edu.fudan.common.entity;

import lombok.Data;

/**
 * @author fdse
 */
@Data
public class PaymentDifferenceInfo {

    private String orderId;

    private String tripId;

    private String userId;

    private String price;

    public PaymentDifferenceInfo(){
        //Default Constructor
    }

}
