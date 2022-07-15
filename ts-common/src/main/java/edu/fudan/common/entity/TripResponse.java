package edu.fudan.common.entity;

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
    private Date startTime;

    @Valid
    @NotNull
    private Date endTime;

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
        this.startTime = new Date();
        this.endTime = new Date();
        this.economyClass = 0;
        this.confortClass = 0;
        this.priceForEconomyClass = "";
        this.priceForConfortClass = "";
    }

}
