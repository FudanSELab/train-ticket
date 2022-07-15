package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInfo {
    private String loginId;

    private String tripId;

    private String trainTypeId;
    private String trainTypeName;

    private String routeId;

    private String startStationId;
    private String startStationName;

    private String stationsId;
    private String stationsName;

    private String terminalStationId;
    private String terminalStationName;

    private Date startTime;

    private Date endTime;

}
