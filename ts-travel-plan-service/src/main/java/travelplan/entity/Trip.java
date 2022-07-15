package travelplan.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class Trip {

    private TripId tripId;

    private String trainTypeId;

    private String routeId;

    private String startStationName;

    private String stationsId;

    private String terminalStationId;

    private Date startingTime;

    private Date endTime;

    public Trip(TripId tripId, String trainTypeId, String startStationName, String stationsId, String terminalStationId, Date startingTime, Date endTime) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startStationName = startStationName;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
    }

    public Trip(){
        //Default Constructor
    }

}
