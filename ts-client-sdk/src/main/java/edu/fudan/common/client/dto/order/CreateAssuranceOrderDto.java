package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload for creating an assurance order for an existing ticket order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssuranceOrderDto {

    /**
     * The ticket order id that the assurance attaches to.
     */
    private String orderId;

    /**
     * Selected assurance type index.
     */
    private int typeIndex;
}


