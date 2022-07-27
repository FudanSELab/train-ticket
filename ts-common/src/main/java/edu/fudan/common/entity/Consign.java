package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consign {
    private String id;        //id主键改成String类型的 自定义生成策略
    private String orderId;   //这次托运关联订单
    private String accountId;  //这次托运关联的账户

    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean isWithin;
}
