package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAlterInfo {
    private String accountId;

    private String previousOrderId;

    private String loginToken;

    private Order newOrderInfo;

    public OrderAlterInfo(String accountId, String previousOrderId, String loginToken){
        this.accountId = accountId;
        this.previousOrderId = previousOrderId;
        this.loginToken = loginToken;
        this.newOrderInfo = new Order();
        this.newOrderInfo.initOrder();
    }
}
