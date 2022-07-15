package edu.fudan.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class SoldTicket {

    private Date travelDate;

    private String trainNumber;

    private int noSeat;

    private int businessSeat;

    private int firstClassSeat;

    private int secondClassSeat;

    private int hardSeat;

    private int softSeat;

    private int hardBed;

    private int softBed;

    private int highSoftBed;

    public SoldTicket(){
        noSeat = 0;
        businessSeat = 0;
        firstClassSeat = 0;
        secondClassSeat = 0;
        hardSeat = 0;
        softSeat = 0;
        hardBed = 0;
        softBed = 0;
        highSoftBed = 0;
    }

}
