package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing order security statistics returned by order-service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSecurityDto {

    /** Number of orders placed in the last one hour */
    private int orderNumInLastOneHour;

    /** Number of valid (unused) orders in total */
    private int orderNumOfValidOrder;
}
