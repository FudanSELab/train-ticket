package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload for updating the assurance type bound to an order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAssuranceOrderTypeDto {

    /**
     * Target assurance type index.
     */
    private int typeIndex;
}


