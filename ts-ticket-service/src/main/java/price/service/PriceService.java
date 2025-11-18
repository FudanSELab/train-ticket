package price.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import price.entity.Price;

import java.util.List;
import java.util.Map;


/**
 * @author fdse
 */
public interface PriceService {

    Response<Price> createPrice(Price priceConfig, HttpHeaders headers);

    Response<Map<String, Price>> findByRouteIdsAndTrainTypes(List<String> ridsAndTts, HttpHeaders headers);

    Response<Price> findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers);

    Response<List<Price>> findAllPrice(HttpHeaders headers);

    Response<Price> deletePrice(String pcId, HttpHeaders headers);

    Response<Price> updatePrice(Price c, HttpHeaders headers);

}
