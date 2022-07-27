package edu.fudan.common.entity;

import java.util.Map;
import lombok.*;

/**
 * @author fdse
 */
@Data
public class TravelResult {

    private boolean status;

    private double percent;

    private TrainType trainType;

    private Route route;

    private Map<String,String> prices;

    public TravelResult(){
        //Default Constructor
    }

    public boolean isStatus() {
        return status;
    }

}
