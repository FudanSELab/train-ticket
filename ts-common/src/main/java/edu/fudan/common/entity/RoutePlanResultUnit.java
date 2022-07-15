package edu.fudan.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
public class RoutePlanResultUnit {

    private String tripId;

    private String trainTypeId;

    private String fromStationName;

    private String toStationName;

    private List<String> stopStations;

    private String priceForSecondClassSeat;

    private String priceForFirstClassSeat;

    private Date startingTime;

    private Date endTime;

}
