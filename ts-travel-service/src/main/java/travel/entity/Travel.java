package travel.entity;

import java.util.Date;


public class Travel extends TripInfo{

    private Trip trip;

    public Travel(){
        //Default Constructor
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    // start

    // end
}
