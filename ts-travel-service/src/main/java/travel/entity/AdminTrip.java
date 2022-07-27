package travel.entity;

import edu.fudan.common.entity.TrainType;
import edu.fudan.common.entity.Route;
import lombok.Data;


/**
 * @author fdse
 */
@Data
public class AdminTrip {
    private Trip trip;
    private TrainType trainType;
    private Route route;

    public AdminTrip(){
        //Default Constructor
    }

}
