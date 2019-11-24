package travelto.entity;

/**
 * Travel class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class Travel extends TripBase {

    private Trip trip;

    public Travel() {
        super();
        //Default Constructor
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
