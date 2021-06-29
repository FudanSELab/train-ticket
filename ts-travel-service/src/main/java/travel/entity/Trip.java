package travel.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@Document(collection="trip")
public class Trip {
    @Valid
    @Id
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    private String routeId;

    private Date startingTime;

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

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
        this.startingStationId = "";
        this.terminalStationId = "";
        this.endTime = new Date();
    }

    public Trip(){
        //Default Constructor
        this.trainTypeId = "";
        this.startingStationId = "";
        this.terminalStationId = "";
        this.endTime = new Date();
    }

    public void addPassenger(long id, PassengerInfo info) { }
}
