package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for querying consign orders by consignee name.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetConsignOrdersByConsigneeDto {

    /**
     * Consignee name used for fuzzy or exact matching.
     */
    private String consignee;
}

