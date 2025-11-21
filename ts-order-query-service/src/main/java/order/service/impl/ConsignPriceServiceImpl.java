package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.ConsignPrice;
import order.repository.ConsignPriceConfigRepository;
import order.service.ConsignPriceService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Local implementation of price calculation logic (ported from ts-consign-price-service).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsignPriceServiceImpl implements ConsignPriceService {

    private final ConsignPriceConfigRepository repository;

    private static final String SUCCESS = "Success";

    @Override
    public Response<Double> getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers) {
        ConsignPrice priceConfig = currentConfig();
        double extraWeight = Math.max(0, weight - priceConfig.getInitialWeight());
        double unitPrice = isWithinRegion ? priceConfig.getWithinPrice() : priceConfig.getBeyondPrice();
        double price = priceConfig.getInitialPrice() + extraWeight * unitPrice;
        return success(price);
    }

    @Override
    public Response<String> queryPriceInformation(HttpHeaders headers) {
        ConsignPrice price = currentConfig();
        String info = String.format(
                "The price of weight within %s is %s. The price of extra weight within the region is %s and beyond the region is %s",
                price.getInitialWeight(),
                price.getInitialPrice(),
                price.getWithinPrice(),
                price.getBeyondPrice());
        return success(info);
    }

    @Override
    public Response<ConsignPrice> getPriceConfig(HttpHeaders headers) {
        return success(currentConfig());
    }

    private ConsignPrice currentConfig() {
        return repository.findByIndex(0);
    }

    private <T> Response<T> success(T data) {
        return new Response<>(1, SUCCESS, data);
    }
}
