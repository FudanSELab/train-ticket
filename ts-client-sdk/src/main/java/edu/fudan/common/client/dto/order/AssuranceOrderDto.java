package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Assurance DTO mirroring the assurance entity enriched with type metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssuranceOrderDto {

    private String id;
    private String orderId;
    private int typeIndex;
    private String typeName;
    private double typePrice;
}


