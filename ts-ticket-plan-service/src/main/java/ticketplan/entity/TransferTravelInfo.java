package travelplan.entity;

import edu.fudan.common.util.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class TransferTravelInfo {

    private String startStation;

    private String viaStation;

    private String endStation;

    private String travelDate;

    private String trainType;

    public TransferTravelInfo() {
        //Empty Constructor
    }

    public TransferTravelInfo(String startStation, String viaStation, String endStation, String travelDate, String trainType) {
        this.startStation = startStation;
        this.viaStation = viaStation;
        this.endStation = endStation;
        this.travelDate = travelDate;
        this.trainType = trainType;
    }

    public Date getTravelDate() {
        return StringUtils.String2Date(travelDate);
    }
}
