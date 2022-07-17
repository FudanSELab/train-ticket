package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutePlanResultUnit {

    private String tripId;

    private String trainTypeId;
    private String trainTypeName;

    private String fromStationName;

    private String toStationName;

    private List<String> stopStations;

    private String priceForSecondClassSeat;

    private String priceForFirstClassSeat;

    private Date startTime;

    private Date endTime;

}
