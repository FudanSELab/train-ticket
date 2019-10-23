package travel.entity;

import java.util.Date;

public class TravelInfo extends TravelInfoAndTripBase{

    private String tripId;

    public TravelInfo() {
        //Default Constructor
    }
    //start
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    // end
}
