package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
public class TripResponse {
    @Valid
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeName;

    @Valid
    @NotNull
    private String startStation;

    @Valid
    @NotNull
    private String terminalStation;

    @Valid
    @NotNull
    private String startTime;

    @Valid
    @NotNull
    private String endTime;

    /**
     * the number of economy seats
     */
    @Valid
    @NotNull
    private int economyClass;

    /**
     * the number of confort seats
     */
    @Valid
    @NotNull
    private int confortClass;

    @Valid
    @NotNull
    private String priceForEconomyClass;

    @Valid
    @NotNull
    private String priceForConfortClass;

    public TripResponse(){
        //Default Constructor
        this.trainTypeName = "";
        this.startStation = "";
        this.terminalStation = "";
        this.startTime = "";
        this.endTime = "";
        this.economyClass = 0;
        this.confortClass = 0;
        this.priceForEconomyClass = "";
        this.priceForConfortClass = "";
    }

//    public Date getStartTime(){
//        return StringUtils.String2Date(startTime);
//    }
//    public Date getEndTime(){
//        return StringUtils.String2Date(endTime);
//    }
//
//    public void setStartTime(Date startTime){
//        this.startTime = StringUtils.Date2String(startTime);
//    }
//    public void setEndTime(Date endTime){
//        this.endTime = StringUtils.Date2String(endTime);
//    }

}
