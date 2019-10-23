package travel.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TripInfo {

    @Valid
    @NotNull
    protected String startingPlace;

    @Valid
    @NotNull
    protected String endPlace;

    @Valid
    @NotNull
    protected Date departureTime;

    public TripInfo(){
        //Default Constructor
    }
    // start
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
    //end
}
