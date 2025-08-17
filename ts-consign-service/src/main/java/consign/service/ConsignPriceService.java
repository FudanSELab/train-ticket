package consign.service;

import consign.entity.ConsignPrice;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * Service defining operations for consign price calculations and configuration.
 */
public interface ConsignPriceService {

    /**
     * Calculate price given weight and region.
     */
    Response getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers);

    /**
     * Get price information summary string.
     */
    Response queryPriceInformation(HttpHeaders headers);

    /**
     * Create or update price configuration.
     */
    Response createAndModifyPrice(ConsignPrice config, HttpHeaders headers);

    /**
     * Retrieve the current price configuration.
     */
    Response getPriceConfig(HttpHeaders headers);
}
