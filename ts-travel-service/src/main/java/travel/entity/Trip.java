package travel.entity;

import edu.fudan.common.entity.TripId;
import edu.fudan.common.util.StringUtils;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Entity
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
    private String startTime;

    @Valid
    @NotNull
    private String endTime;

    public Trip(edu.fudan.common.entity.TripId tripId, String trainTypeName, String startStationName, String stationsName, String terminalStationName, Date startTime, Date endTime) {
        this.tripId = tripId;
        this.trainTypeName = trainTypeName;
        this.startStationName = StringUtils.String2Lower(startStationName);
        this.stationsName = StringUtils.String2Lower(stationsName);
        this.terminalStationName = StringUtils.String2Lower(terminalStationName);
        this.startTime = StringUtils.Date2String(startTime);
        this.endTime = StringUtils.Date2String(endTime);
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

    public Date getStartTime(){
        return StringUtils.String2Date(this.startTime);
    }

    public Date getEndTime(){
        return StringUtils.String2Date(this.endTime);
    }

    public void setStartTime(Date startTime){
        this.startTime = StringUtils.Date2String(startTime);
    }

    public void setEndTime(Date endTime){
        this.endTime = StringUtils.Date2String(endTime);
    }
}