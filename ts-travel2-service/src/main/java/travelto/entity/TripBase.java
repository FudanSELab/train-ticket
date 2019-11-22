package travelto.entity;

import java.util.Date;

/**
 * @author: QiaoJim
 * Date:  2019/11/19
 * Desc:
 */

public class TripBase {

    protected String startingPlace;

    protected String endPlace;

    protected Date departureTime;

    public String getStartingPlace() {
        return startingPlace;
    }

    public void setStartingPlace(String startingPlace) {
        this.startingPlace = startingPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
