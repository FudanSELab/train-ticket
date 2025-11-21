package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used by ts-order-service for modifying an order status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyOrderStatusDto {

    /**
     * Target order status code.
     */
    private int status;
}


