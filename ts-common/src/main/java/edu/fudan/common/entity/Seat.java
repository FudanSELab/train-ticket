package edu.fudan.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
public class Seat {
    @Valid
    @NotNull
    private Date travelDate;

    @Valid
    @NotNull
    private String trainNumber;


    @Valid
    @NotNull
    private String startStation;

    @Valid
    @NotNull
    private String destStation;

    @Valid
    @NotNull
    private int seatType;

    private int totalNum;

    private List<String> stations;

    public Seat(){
        //Default Constructor
        this.travelDate = new Date();
        this.trainNumber = "";
        this.startStation = "";
        this.destStation = "";
        this.seatType = 0;
        this.totalNum = 0;
        this.stations = null;
    }

}
