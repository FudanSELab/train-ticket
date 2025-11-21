package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Assurance type metadata.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssuranceTypeDto {

    private int index;
    private String name;
    private double price;
}


