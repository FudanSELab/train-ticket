package edu.fudan.common.entity;

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
    private String trainTypeId;

    private String routeId;


    @Valid
    @NotNull
    private String startStationName;

    @Valid
    private String stationsId;

    @Valid
    @NotNull
    private String terminalStationId;

    @Valid
    @NotNull
    private Date startTime;

    @Valid
    @NotNull
    private Date endTime;

    public Trip(TripId tripId, String trainTypeId, String startStationName, String stationsId, String terminalStationId, Date startingTime, Date endTime) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startStationName = startStationName;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        this.id = UUID.randomUUID().toString();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
        this.startStationName = "";
        this.terminalStationId = "";
        this.startTime = new Date();
        this.endTime = new Date();
    }

    public Trip(){
        //Default Constructor
        this.id = UUID.randomUUID().toString();
        this.trainTypeId = "";
        this.startStationName = "";
        this.terminalStationId = "";
        this.startTime = new Date();
        this.endTime = new Date();
    }

}