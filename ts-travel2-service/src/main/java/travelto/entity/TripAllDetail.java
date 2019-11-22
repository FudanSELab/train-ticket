package travelto.entity;
/**
 * TripAllDetail class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class TripAllDetail {

    private boolean status;

    private String message;

    private TripResponse tripResponse;

    private Trip trip;

    public TripAllDetail() {
        //Default Constructor
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TripResponse getTripResponse() {
        return tripResponse;
    }

    public void setTripResponse(TripResponse tripResponse) {
        this.tripResponse = tripResponse;
    }
}
