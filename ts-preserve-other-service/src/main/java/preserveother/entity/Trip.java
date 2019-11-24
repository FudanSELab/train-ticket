package preserveother.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdu
 */
public class Trip {
    @Valid
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    private String routeId;


    @Valid
    @NotNull
    private String startingStationId;

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
        this.startingStationId = startingStationId;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(String trainTypeId, String startingStationId, String terminalStationId, Date startingTime, Date endTime) {
        this.trainTypeId = trainTypeId;
        this.startingStationId = startingStationId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
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
