package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
public class RoutePlanInfo {

    private String startStation;

    private String endStation;

    private String travelDate;

    private int num;

    public RoutePlanInfo() {
        //Empty Constructor
    }

    public String getStartStation() {
        return StringUtils.String2Lower(this.startStation);
    }

    public String getEndStation() {
        return StringUtils.String2Lower(this.endStation);
    }
}
