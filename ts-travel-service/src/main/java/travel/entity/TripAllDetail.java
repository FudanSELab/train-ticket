package travel.entity;

import edu.fudan.common.entity.TripResponse;
import lombok.Data;

import edu.fudan.common.entity.TripResponse;

/**
 * @author fdse
 */
@Data
public class TripAllDetail {
    private TripResponse tripResponse;

    private Trip trip;

    public TripAllDetail() {
    }

    public TripAllDetail(TripResponse tripResponse, Trip trip) {
        this.tripResponse = tripResponse;
        this.trip = trip;
    }

}
