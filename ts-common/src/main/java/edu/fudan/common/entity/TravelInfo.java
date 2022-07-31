package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelInfo {
    private String loginId;

    private String tripId;

    private String trainTypeName;

    private String routeId;

    private String startStationName;

    private String stationsName;

    private String terminalStationName;

    private String startTime;

    private String endTime;

//    public Date getStartTime(){
//        return StringUtils.String2Date(this.startTime);
//    }
//
//    public Date getEndTime(){
//        return StringUtils.String2Date(this.endTime);
//    }
//
//    public String getStartStationName() {
//        return StringUtils.String2Lower(this.startStationName);
//    }
//
//    public String getTerminalStationName() {
//        return StringUtils.String2Lower(this.terminalStationName);
//    }

}
