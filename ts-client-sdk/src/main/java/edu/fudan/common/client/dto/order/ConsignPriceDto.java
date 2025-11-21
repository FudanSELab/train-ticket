package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the consign price configuration exposed by order-service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsignPriceDto {

    /**
     * Primary key of the configuration row (uuid).
     */
    private String id;

    /**
     * Configuration index, always 0 because there is only one row.
     */
    private Integer index;

    /**
     * Maximum weight covered by the initial price.
     */
    private double initialWeight;

    /**
     * Price applied when the package weight is within the initial weight.
     */
    private double initialPrice;

    /**
     * Price per kilogram for the extra weight inside the region.
     */
    private double withinPrice;

    /**
     * Price per kilogram for the extra weight beyond the region.
     */
    private double beyondPrice;
}


