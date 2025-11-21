package order.service;

import order.entity.ConsignPrice;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * Service defining operations for consign price calculations and configuration.
 */
public interface ConsignPriceService {

    /**
     * Calculate price given weight and region.
     */
    Response<Double> getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers);

    /**
     * Get price information summary string.
     */
    Response<String> queryPriceInformation(HttpHeaders headers);

    /**
     * Retrieve the current price configuration.
     */
    Response<ConsignPrice> getPriceConfig(HttpHeaders headers);
}
