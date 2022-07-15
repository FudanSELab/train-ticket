package edu.fudan.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class RoutePlanInfo {

    private String formStationName;

    private String toStationName;

    private Date travelDate;

    private int num;

    public RoutePlanInfo() {
        //Empty Constructor
    }

    public RoutePlanInfo(String formStationName, String toStationName, Date travelDate, int num) {
        this.formStationName = formStationName;
        this.toStationName = toStationName;
        this.travelDate = travelDate;
        this.num = num;
    }

}
