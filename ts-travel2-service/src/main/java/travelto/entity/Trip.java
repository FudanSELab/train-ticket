package travelto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.Date;

/**
 * Trip class
 *
 * @author fdu
 * @date 2019/11/10
 */
@Document(collection = "trip")
public class Trip extends TravelBase {
    @Valid
    @Id
    private TripId tripId;

    public Trip(TripId tripId, String trainTypeId, String startingStationId, String stationsId, String terminalStationId, Date startingTime, Date endTime) {
        super();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.startingStationId = startingStationId;
        this.stationsId = stationsId;
        this.terminalStationId = terminalStationId;
        this.startingTime = startingTime;
        this.endTime = endTime;
    }

    public Trip(TripId tripId, String trainTypeId, String routeId) {
        super();
        this.tripId = tripId;
        this.trainTypeId = trainTypeId;
        this.routeId = routeId;
    }

    public Trip() {
        super();
        //Default Constructor
    }

    public TripId getTripId() {
        return tripId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }
}
