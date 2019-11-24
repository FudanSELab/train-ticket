package preserveother.entity;

import java.util.Date;

/**
 * @author fdu
 */
public class Seat {

    private Date travelDate;

    private String trainNumber;

    private String startStation;

    private String destStation;

    private int seatType;

    public Seat() {
        //Default Constructor
    }

    public Seat(Date travelDate, String trainNumber, String startStation, String destStation, int seatType) {
        this.travelDate = travelDate;
        this.trainNumber = trainNumber;
        this.startStation = startStation;
        this.destStation = destStation;
        this.seatType = seatType;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getDestStation() {
        return destStation;
    }

    public void setDestStation(String destStation) {
        this.destStation = destStation;
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }
}
