package edu.fudan.common.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author fdse
 */
@Data
@ToString
public class TripAllDetailInfo {

    private String tripId;

    private Date travelDate;

    private String from;

    private String to;

    public TripAllDetailInfo() {
        //Default Constructor
    }

}
