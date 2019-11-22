package travelto.entity;

import java.util.Date;
/**
 * SoldTicket class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class SoldTicket {

    private Date travelDate;

    private String trainNumber;

    private int noSeat;

    private int businessSeat;

    private int firstClassSeat;

    private int secondClassSeat;

    private int hardSeat;

    private int softSeat;

    private int hardBed;

    private int softBed;

    private int highSoftBed;

    public SoldTicket(){
        noSeat = 0;
        businessSeat = 0;
        firstClassSeat = 0;
        secondClassSeat = 0;
        hardSeat = 0;
        softSeat = 0;
        hardBed = 0;
        softBed = 0;
        highSoftBed = 0;
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

    public int getNoSeat() {
        return noSeat;
    }

    public void setNoSeat(int noSeat) {
        this.noSeat = noSeat;
    }

    public int getBusinessSeat() {
        return businessSeat;
    }

    public void setBusinessSeat(int businessSeat) {
        this.businessSeat = businessSeat;
    }

    public int getFirstClassSeat() {
        return firstClassSeat;
    }

    public void setFirstClassSeat(int firstClassSeat) {
        this.firstClassSeat = firstClassSeat;
    }

    public int getSecondClassSeat() {
        return secondClassSeat;
    }

    public void setSecondClassSeat(int secondClassSeat) {
        this.secondClassSeat = secondClassSeat;
    }

    public int getHardSeat() {
        return hardSeat;
    }

    public void setHardSeat(int hardSeat) {
        this.hardSeat = hardSeat;
    }

    public int getSoftSeat() {
        return softSeat;
    }

    public void setSoftSeat(int softSeat) {
        this.softSeat = softSeat;
    }

    public int getHardBed() {
        return hardBed;
    }

    public void setHardBed(int hardBed) {
        this.hardBed = hardBed;
    }

    public int getSoftBed() {
        return softBed;
    }

    public void setSoftBed(int softBed) {
        this.softBed = softBed;
    }

    public int getHighSoftBed() {
        return highSoftBed;
    }

    public void setHighSoftBed(int highSoftBed) {
        this.highSoftBed = highSoftBed;
    }
}
