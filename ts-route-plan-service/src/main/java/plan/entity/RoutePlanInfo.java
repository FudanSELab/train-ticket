package plan.entity;

import java.util.Date;

public class RoutePlanInfo {

    private String fromStationName;

    private String toStationName;

    private Date travelDate;

    private int num;

    public RoutePlanInfo() {
        //Empty Constructor
    }

    public RoutePlanInfo(String formStationName, String toStationName, Date travelDate, int num) {
        this.fromStationName = formStationName;
        this.toStationName = toStationName;
        this.travelDate = travelDate;
        this.num = num;
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

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
