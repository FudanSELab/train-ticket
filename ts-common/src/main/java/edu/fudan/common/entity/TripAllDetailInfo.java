package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
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

    private String travelDate;

    private String from;

    private String to;

    public TripAllDetailInfo() {
        //Default Constructor
    }

    public String getFrom() {
        return StringUtils.String2Lower(this.from);
    }

    public String getTo() {
        return StringUtils.String2Lower(this.to);
    }

//    public Date getTravelDate() {
//        return StringUtils.String2Date(travelDate);
//    }
//
//    public void setTravelDate(Date travelDate) {
//        this.travelDate = StringUtils.Date2String(travelDate);
//    }
}
