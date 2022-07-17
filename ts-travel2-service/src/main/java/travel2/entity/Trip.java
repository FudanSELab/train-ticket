package travel2.entity;

import edu.fudan.common.entity.TripId;
import edu.fudan.common.util.StringUtils;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@Entity
@Table(name = "trip2")
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class Trip {
    @Valid
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    @Embedded
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

    public Trip(TripId tripId, String trainTypeName, String startStationName, String stationsName, String terminalStationName, Date startTime, Date endTime) {
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
        this.startTime = new Date();
        this.endTime = new Date();
    }

    public Trip(){
        //Default Constructor
        this.trainTypeName = "";
        this.startStationName = "";
        this.terminalStationName = "";
        this.startTime = new Date();
        this.endTime = new Date();
    }

}