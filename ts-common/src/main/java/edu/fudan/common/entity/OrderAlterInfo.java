package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author fdse
 */
@Data
@AllArgsConstructor
public class OrderAlterInfo {
    private String accountId;

    private String previousOrderId;

    private String loginToken;

    private Order newOrderInfo;

    public OrderAlterInfo(){
        newOrderInfo = new Order();
    }
}
