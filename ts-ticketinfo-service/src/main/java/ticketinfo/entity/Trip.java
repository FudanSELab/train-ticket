package ticketinfo.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;


public class Trip {
    @Valid
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    private String routeId;

    //-------------------------------以下变量暂时停用-----------------------
    @Valid
    @NotNull
    private String startingStationId;

    //中间停靠站，最开始的版本只设置一站，也就是说只有起始站、一个停靠站、终点站，在之后的版本中，停靠站扩展为若干站
    @Valid
    private String stationsId;

    @Valid
    @NotNull
    private String terminalStationId;

    @Valid
    @NotNull
    private Date startingTime;

    @Valid
    @NotNull
    private Date endTime;

    public Trip(TripId tripId, String trainTypeId, String startingStationId, String stationsId, String terminalStationId, Date startingTime, Date endTime) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = ""; 
        this.startingStationId = startingStationId;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
        this.startingStationId = "";
        this.stationsId = "";
        this.terminalStationId = "";
        this.startingTime = new Date();
        this.endTime = new Date();
    }

    public Trip(){
        this.tripId = new TripId("G000");
        this.trainTypeId = "";
        this.routeId = "";
        this.startingStationId = "";
        this.stationsId = "";
        this.terminalStationId = "";
        this.startingTime = new Date();
        this.endTime = new Date();
        //Default Constructor
    }

    public TripId getTripId() {
        return tripId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getStartingStationId() {
        return startingStationId;
    }

    public void setStartingStationId(String startingStationId) {
        this.startingStationId = startingStationId;
    }

    public String getStationsId() {
        return stationsId;
    }

    public void setStationsId(String stationsId) {
        this.stationsId = stationsId;
    }

    public String getTerminalStationId() {
        return terminalStationId;
    }

    public void setTerminalStationId(String terminalStationId) {
        this.terminalStationId = terminalStationId;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
