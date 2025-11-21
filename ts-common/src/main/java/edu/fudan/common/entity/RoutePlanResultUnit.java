package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutePlanResultUnit {

    private String tripId;

    private String trainTypeName;

    private String startStation;

    private String endStation;

    private List<String> stopStations;

    private String priceForSecondClassSeat;

    private String priceForFirstClassSeat;

    private String startTime;

    private String endTime;


}
