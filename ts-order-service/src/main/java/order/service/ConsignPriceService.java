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
     * Create a new price configuration.
     */
    Response<ConsignPrice> createPriceConfig(ConsignPrice config, HttpHeaders headers);

    /**
     * Update the existing price configuration.
     */
    Response<ConsignPrice> updatePriceConfig(ConsignPrice config, HttpHeaders headers);
}
