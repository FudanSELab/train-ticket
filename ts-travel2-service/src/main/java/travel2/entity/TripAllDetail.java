package travel2.entity;

import edu.fudan.common.entity.TripResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fdse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripAllDetail {

    private boolean status;

    private String message;

    private TripResponse tripResponse;

    private Trip trip;

}
