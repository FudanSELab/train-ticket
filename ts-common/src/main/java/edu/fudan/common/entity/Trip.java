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
    private String id;

    private TripId tripId;

    private String trainTypeName;

    private String routeId;
    private String startStationName;

    private String stationsName;

    private String terminalStationName;

    private String startTime;

    private String endTime;

    public Trip(edu.fudan.common.entity.TripId tripId, String trainTypeName, String startStationName, String stationsName, String terminalStationName, String startTime, String endTime) {
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.startStationName = StringUtils.String2Lower(startStationName);
        this.stationsName = StringUtils.String2Lower(stationsName);
        this.terminalStationName = StringUtils.String2Lower(terminalStationName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeName, String routeId) {
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.routeId = routeId;
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = "";
        this.endTime = "";
    }

    public Trip(){
        //Default Constructor
        this.trainTypeName = "";
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = "";
        this.endTime = "";
    }

//    public Date getStartTime(){
//        return StringUtils.String2Date(this.startTime);
//    }
//
//    public Date getEndTime(){
//        return StringUtils.String2Date(this.endTime);
//    }
//
//    public void setStartTime(Date startTime){
//        this.startTime = StringUtils.Date2String(startTime);
//    }
//
//    public void setEndTime(Date endTime){
//        this.endTime = StringUtils.Date2String(endTime);
//    }

}