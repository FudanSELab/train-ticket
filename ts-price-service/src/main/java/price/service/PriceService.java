package price.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import price.entity.PriceConfig;


/**
 * @author fdse
 */
public interface PriceService {

    Response createNewPriceConfig(PriceConfig priceConfig, HttpHeaders headers);

    PriceConfig findById(String id, HttpHeaders headers);

    Response findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers);

    Response findAllPriceConfig(HttpHeaders headers);

    Response deletePriceConfig(String pcId, HttpHeaders headers);

    Response updatePriceConfig(PriceConfig c, HttpHeaders headers);

}
