package plan.entity;

import java.util.Date;
import java.util.List;

public class RoutePlanResultUnit {

    private String tripId;

    private String trainTypeId;

    private String fromStationName;

    private String toStationName;

    private List<String> stopStations;

    private String priceForSecondClassSeat;

    private String priceForFirstClassSeat;

    private Date startingTime;

    private Date endTime;

    public RoutePlanResultUnit() {
        //Default Constructor
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public List<String> getStopStations() {
        return stopStations;
    }

    public void setStopStations(List<String> stopStations) {
        this.stopStations = stopStations;
    }

    public String getPriceForSecondClassSeat() {
        return priceForSecondClassSeat;
    }

    public void setPriceForSecondClassSeat(String priceForSecondClassSeat) {
        this.priceForSecondClassSeat = priceForSecondClassSeat;
    }

    public String getPriceForFirstClassSeat() {
        return priceForFirstClassSeat;
    }

    public void setPriceForFirstClassSeat(String priceForFirstClassSeat) {
        this.priceForFirstClassSeat = priceForFirstClassSeat;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
