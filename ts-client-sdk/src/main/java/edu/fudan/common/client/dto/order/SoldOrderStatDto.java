package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Aggregated seat statistics for a train on a specific travel date.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoldOrderStatDto {

    /**
     * Travel date associated with the statistics.
     */
    private Date travelDate;

    /**
     * Train number for which the statistics are calculated.
     */
    private String trainNumber;

    private int noSeat;

    private int businessSeat;

    private int firstClassSeat;

    private int secondClassSeat;

    private int hardSeat;

    private int softSeat;

    private int hardBed;

    private int softBed;

    private int highSoftBed;
}


