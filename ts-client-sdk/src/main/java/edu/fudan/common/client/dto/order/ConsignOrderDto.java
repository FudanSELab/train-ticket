package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consign DTO mirroring consign request/record entities exposed via order-service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsignOrderDto {

    private String id;
    private String orderId;
    private String userId;
    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean within;
    private Double price;
}


