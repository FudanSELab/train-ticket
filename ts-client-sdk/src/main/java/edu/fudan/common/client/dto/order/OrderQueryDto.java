package edu.fudan.common.client.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query parameters used by order-query endpoints when filtering orders.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDto {

    /**
     * Account identifier (login id) issuing the query.
     */
    private String loginId;

    private String travelDateStart;

    private String travelDateEnd;

    private String boughtDateStart;

    private String boughtDateEnd;

    private int state;

    private boolean enableTravelDateQuery;

    private boolean enableBoughtDateQuery;

    private boolean enableStateQuery;

    public void enableTravelDateQuery(String startTime, String endTime) {
        this.enableTravelDateQuery = true;
        this.travelDateStart = startTime;
        this.travelDateEnd = endTime;
    }

    public void disableTravelDateQuery() {
        this.enableTravelDateQuery = false;
        this.travelDateStart = null;
        this.travelDateEnd = null;
    }

    public void enableBoughtDateQuery(String startTime, String endTime) {
        this.enableBoughtDateQuery = true;
        this.boughtDateStart = startTime;
        this.boughtDateEnd = endTime;
    }

    public void disableBoughtDateQuery() {
        this.enableBoughtDateQuery = false;
        this.boughtDateStart = null;
        this.boughtDateEnd = null;
    }

    public void enableStateQuery(int targetStatus) {
        this.enableStateQuery = true;
        this.state = targetStatus;
    }

    public void disableStateQuery() {
        this.enableStateQuery = false;
        this.state = -1;
    }
}


