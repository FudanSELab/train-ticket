package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for calculating consign prices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsignPriceQueryDto {

    /**
     * Package weight in kilograms.
     */
    private double weight;

    /**
     * Whether the delivery stays within a single region.
     */
    private boolean withinRegion;
}


