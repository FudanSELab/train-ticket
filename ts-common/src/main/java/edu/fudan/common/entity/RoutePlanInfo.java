package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class RoutePlanInfo {

    private String startStation;

    private String endStation;

    private String travelDate;

    private int num;

    public RoutePlanInfo() {
        //Empty Constructor
    }

    public RoutePlanInfo(String startStation, String endStation, String travelDate, int num) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.travelDate = travelDate;
        this.num = num;
    }

    public Date getTravelDate() {
        return StringUtils.String2Date(travelDate);
    }
}
