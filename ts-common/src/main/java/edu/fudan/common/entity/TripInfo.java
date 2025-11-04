package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
public class TripInfo {
    @Valid
    @NotNull
    private String startPlace;

    @Valid
    @NotNull
    private String endPlace;

    @Valid
    @NotNull
    private String departureTime;

    public TripInfo(){
        //Default Constructor
        this.startPlace = "";
        this.endPlace = "";
        this.departureTime = "";
    }

    public TripInfo(String startPlace, String endPlace, String departureTime) {
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.departureTime = departureTime;
    }

    public String getStartPlace() {
        return StringUtils.String2Lower(this.startPlace);
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return StringUtils.String2Lower(this.endPlace);
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

//    public Date getDepartureTime(){
//        return StringUtils.String2Date(this.departureTime);
//    }
}
