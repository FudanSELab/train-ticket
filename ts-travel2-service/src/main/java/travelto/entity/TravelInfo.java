package travelto.entity;

/**
 * TravelInfo class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class TravelInfo extends TravelBase {

    private String tripId;

    public TravelInfo() {
        super();
        //Default Constructor
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
