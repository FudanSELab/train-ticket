package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
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
    public Response<ConsignPrice> createPriceConfig(ConsignPrice config, HttpHeaders headers) {
      log.info("[Create new price config][config: {}]", config);

      ConsignPrice existing = currentConfig();
      if (existing != null) {
          log.warn("[createPriceConfig][Price config already exists]");
          return failure("Price config already exists. Use update instead.", existing);
      }

      ConsignPrice target = copyValues(config, new ConsignPrice());
      target.setId(UUID.randomUUID().toString());
      ConsignPrice saved = repository.save(target);
      return success(saved);
    }

    @Override
    public Response<ConsignPrice> updatePriceConfig(ConsignPrice config, HttpHeaders headers) {
      log.info("[Update price config][config: {}]", config);

      ConsignPrice existing = currentConfig();
      ConsignPrice target = copyValues(config, existing);
      target.setId(existing.getId());
      ConsignPrice saved = repository.save(target);
      return success(saved);
    }

    private ConsignPrice currentConfig() {
        return repository.findByIndex(0);
    }

    private <T> Response<T> success(T data) {
        return new Response<>(1, SUCCESS, data);
    }

    private <T> Response<T> failure(String message, T data) {
        return new Response<>(0, message, data);
    }

    private ConsignPrice copyValues(ConsignPrice source, ConsignPrice target) {
        target.setId(source.getId());
        target.setIndex(0);
        target.setInitialPrice(source.getInitialPrice());
        target.setInitialWeight(source.getInitialWeight());
        target.setWithinPrice(source.getWithinPrice());
        target.setBeyondPrice(source.getBeyondPrice());
        return target;
    }
}
