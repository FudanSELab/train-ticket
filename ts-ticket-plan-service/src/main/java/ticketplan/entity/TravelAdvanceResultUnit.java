package travelplan.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fdse
 */
@Data
public class TravelAdvanceResultUnit {

    private String tripId;

    private String trainTypeId;

    private String startStation;

    private String endStation;

    private List<String> stopStations;

    private String priceForSecondClassSeat;

    private int numberOfRestTicketSecondClass;

    private String priceForFirstClassSeat;

    private int numberOfRestTicketFirstClass;

    private String startTime;

    private String endTime;

    public TravelAdvanceResultUnit() {
        //Default Constructor
    }

}
