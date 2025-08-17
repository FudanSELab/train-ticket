package consign.service;

import consign.entity.ConsignPrice;
import consign.repository.ConsignPriceConfigRepository;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Local implementation of price calculation logic (ported from ts-consign-price-service).
 */
@Service
public class ConsignPriceServiceImpl implements ConsignPriceService {

    @Autowired
    private ConsignPriceConfigRepository repository;

    private static final String SUCCESS = "Success";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsignPriceServiceImpl.class);

    @Override
    public Response getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers) {
        ConsignPrice priceConfig = repository.findByIndex(0);
        double price;
        double initialPrice = priceConfig.getInitialPrice();
        if (weight <= priceConfig.getInitialWeight()) {
            price = initialPrice;
        } else {
            double extraWeight = weight - priceConfig.getInitialWeight();
            if (isWithinRegion) {
                price = initialPrice + extraWeight * priceConfig.getWithinPrice();
            } else {
                price = initialPrice + extraWeight * priceConfig.getBeyondPrice();
            }
        }
        return new Response<>(1, SUCCESS, price);
    }

    @Override
    public Response queryPriceInformation(HttpHeaders headers) {
        ConsignPrice price = repository.findByIndex(0);
        StringBuilder sb = new StringBuilder();
        sb.append("The price of weight within ")
          .append(price.getInitialWeight())
          .append(" is ")
          .append(price.getInitialPrice())
          .append(". The price of extra weight within the region is ")
          .append(price.getWithinPrice())
          .append(" and beyond the region is ")
          .append(price.getBeyondPrice());
        return new Response<>(1, SUCCESS, sb.toString());
    }

    @Override
    public Response createAndModifyPrice(ConsignPrice config, HttpHeaders headers) {
        LOGGER.info("[createAndModifyPrice][Create New Price Config]");
        ConsignPrice originalConfig = repository.findByIndex(0);
        if (originalConfig == null) {
            originalConfig = new ConsignPrice();
        }
        originalConfig.setId(config.getId());
        originalConfig.setIndex(0);
        originalConfig.setInitialPrice(config.getInitialPrice());
        originalConfig.setInitialWeight(config.getInitialWeight());
        originalConfig.setWithinPrice(config.getWithinPrice());
        originalConfig.setBeyondPrice(config.getBeyondPrice());
        repository.save(originalConfig);
        return new Response<>(1, SUCCESS, originalConfig);
    }

    @Override
    public Response getPriceConfig(HttpHeaders headers) {
        return new Response<>(1, SUCCESS, repository.findByIndex(0));
    }
}
