package preserveother.entity;

import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdu
 */
@ToString
public class TripResponse {
    @Valid
    private TripId tripId;

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

    /**
     * 普通座的座位数量
     */
    @Valid
    @NotNull
    private int economyClass;

    /**
     * 商务座的座位数量
     */
    @Valid
    @NotNull
    private int confortClass;

    public TripResponse(TripId tripId, String startingStation, String terminalStation, Date startingTime, Date endTime, int economyClass, int confortClass) {
        this.tripId = tripId;
        this.startingStation = startingStation;
        this.terminalStation = terminalStation;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
    }

    public TripId getTripId() {
        return tripId;
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
}
