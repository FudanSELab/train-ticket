package travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author cns
 */
@Data
@AllArgsConstructor
public class PassengersInfo {

    @Valid
    @NotNull
    public TripInfo tripInfo;

    @Valid
    @NotNull
    public PassengerInfo[] passengers;

    public PassengersInfo(){
        //Default Constructor
        this.passengers = new PassengerInfo[1];
        this.tripInfo = new TripInfo();
    }

}
