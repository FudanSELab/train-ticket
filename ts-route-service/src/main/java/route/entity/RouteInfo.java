package route.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {
    private String Id;

    private String startStation;

    private String endStation;

    private String stationList;

    private String distanceList;

}
