package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for querying sold tickets by travel date and train number.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelDateNumberDto {

    /**
     * Travel date in yyyy-MM-dd format.
     */
    private String travelDate;

    /**
     * Train number associated with the query.
     */
    private String travelNumber;
}


