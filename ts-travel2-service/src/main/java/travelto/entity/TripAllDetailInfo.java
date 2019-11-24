package travelto.entity;

import lombok.ToString;

import java.util.Date;
/**
 * TripAllDetailInfo class
 *
 * @author fdu
 * @date 2019/11/10
 */
@ToString
public class TripAllDetailInfo {

    private String tripId;

    private Date travelDate;

    private String from;

    private String to;

    public TripAllDetailInfo() {
        //Default Constructor
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
