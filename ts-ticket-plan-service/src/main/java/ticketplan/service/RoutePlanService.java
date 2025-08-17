package plan.service;

import edu.fudan.common.entity.RoutePlanInfo;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface RoutePlanService {

    Response searchCheapestResult(RoutePlanInfo info, HttpHeaders headers);

    Response searchQuickestResult(RoutePlanInfo info, HttpHeaders headers);

    Response searchMinStopStations(RoutePlanInfo info, HttpHeaders headers);
}
