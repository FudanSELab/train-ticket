package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
public class Trip {
    @Valid
    private String id;

    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeName;

    private String routeId;


    @Valid
    @NotNull
    private String startStationName;

    @Valid
    private String stationsName;

    @Valid
    @NotNull
    private String terminalStationName;

    @Valid
    @NotNull
    private Date startTime;

    @Valid
    @NotNull
    private Date endTime;

    public Trip(TripId tripId, String trainTypeName, String startStationName, String stationsName, String terminalStationId, Date startTime, Date endTime) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.startStationName = StringUtils.String2Lower(startStationName);
        this.stationsName = StringUtils.String2Lower(stationsName);
        this.terminalStationName = StringUtils.String2Lower(terminalStationName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeName, String routeId) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.routeId = routeId;
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = new Date();
        this.endTime = new Date();
    }

    public Trip(){
        //Default Constructor
        this.id = UUID.randomUUID().toString();
        this.trainTypeName = "";
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = new Date();
        this.endTime = new Date();
    }

}