package travelto.entity;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * TripResponse class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class TripResponse {
    @Valid
    @Id
    private TripId tripId;

    @Valid
    @NotNull
    private String trainTypeId;

    @Valid
    @NotNull
    private String startingStation;

    @Valid
    @NotNull
    private String terminalStation;

    @Valid
    @NotNull
    private Date startingTime;

    @Valid
    @NotNull
    private Date endTime;
    @Valid
    @NotNull
    /**
     * 普通座的座位数量
     */
    private int economyClass;


    @Valid
    @NotNull
    /**
     * 商务座的座位数量
     */
    private int confortClass;

    @Valid
    @NotNull
    private String priceForEconomyClass;

    @Valid
    @NotNull
    private String priceForConfortClass;

    public TripResponse() {
        //Default Constructor
        trainTypeId = "";
        startingStation = "";
        terminalStation = "";
        startingTime = new Date();
        endTime = new Date();
        economyClass = 0;
        confortClass = 0;
        priceForEconomyClass = "";
        priceForConfortClass = "";
    }

    public TripId getTripId() {
        return tripId;
    }

    public String getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(String trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(String terminalStation) {
        this.terminalStation = terminalStation;
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

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }

    public String getPriceForEconomyClass() {
        return priceForEconomyClass;
    }

    public void setPriceForEconomyClass(String priceForEconomyClass) {
        this.priceForEconomyClass = priceForEconomyClass;
    }

    public String getPriceForConfortClass() {
        return priceForConfortClass;
    }

    public void setPriceForConfortClass(String priceForConfortClass) {
        this.priceForConfortClass = priceForConfortClass;
    }
}
