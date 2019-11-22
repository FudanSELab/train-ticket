package travelto.entity;

/**
 * AdminTrip class
 *
 * @author fdu
 * @date 2019/11/10
 */

public class AdminTrip {
    private Trip trip;
    private TrainType trainType;
    private Route route;

    public AdminTrip() {
        //default constructor
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TrainType getTrainType() {
        return trainType;
    }

    public void setTrainType(TrainType trainType) {
        this.trainType = trainType;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
