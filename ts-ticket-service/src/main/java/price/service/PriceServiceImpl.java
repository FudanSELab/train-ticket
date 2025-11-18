package price.service;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import price.entity.Price;
import price.repository.PriceConfigRepository;

import java.util.*;

/**
 * @author fdse
 */
@Slf4j
@Service
public class PriceServiceImpl implements PriceService {

  @Autowired(required = true)
  private PriceConfigRepository priceConfigRepository;

  String noThatConfig = "No that config";

  public Price findById(String id, HttpHeaders headers) {
    log.info("[findById][ID: {}]", id);
    Optional<Price> op = priceConfigRepository.findById(UUID.fromString(id).toString());
    return op.isPresent() ? op.get() : null;
  }

  @Override
  public Response<Price> createPrice(Price config, HttpHeaders headers) {
    log.info("[createNewPrice] [Price: {}]", config);
    Price priceConfig = null;
    priceConfig = new Price();
    priceConfig.setId(UUID.randomUUID().toString());
    priceConfig.setBasicPriceRate(config.getBasicPriceRate());
    priceConfig.setFirstClassPriceRate(config.getFirstClassPriceRate());
    priceConfig.setRouteId(config.getRouteId());
    priceConfig.setTrainType(config.getTrainType());
    priceConfigRepository.save(priceConfig);
    return new Response<>(1, "Create success", priceConfig);
  }

  @Override
  public Response<Price> findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
    log.info("[findByRouteIdAndTrainType][Route: {} , Train Type: {}]", routeId, trainType);
    Price priceConfig = priceConfigRepository.findByRouteIdAndTrainType(routeId, trainType);

    if (priceConfig == null) {
      log.warn(
          "[findByRouteIdAndTrainType][Find by route and train type warn][PricrConfig not found][RouteId: {}, TrainType: {}]",
          routeId, trainType);
      return new Response<>(0, noThatConfig, null);
    }

    return new Response<>(1, "Success", priceConfig);
  }

  @Override
  public Response<Map<String, Price>> findByRouteIdsAndTrainTypes(List<String> ridsAndTts, HttpHeaders headers) {
    List<String> routeIds = new ArrayList<>();
    List<String> trainTypes = new ArrayList<>();

    for (String rts : ridsAndTts) {
      String[] rt = rts.split(":");
      if (rt.length != 2) {
        log.warn( "[findByRouteIdsAndTrainTypes] route and train type is invalid: {}", rts);
        return new Response<>(0, "Invalid route and train type: " + rts, null);
      }
      routeIds.add(rt[0]);
      trainTypes.add(rt[1]);
    }

    List<Price> prices = priceConfigRepository.findByRouteIdsAndTrainTypes(routeIds, trainTypes);
    Map<String, Price> pcMap = new HashMap<>();
    for (Price price : prices) {
      pcMap.put(price.getRouteId() + ":" + price.getTrainType(), price);
    }
    return new Response<>(1, "Success", pcMap);
  }

  @Override
  public Response<List<Price>> findAllPrice(HttpHeaders headers) {
    List<Price> list = priceConfigRepository.findAll();
    return new Response<>(1, "Success", list);
  }

  @Override
  public Response<Price> deletePrice(String pcId, HttpHeaders headers) {
    Optional<Price> op = priceConfigRepository.findById(pcId);
    if (!op.isPresent()) {
      log.error("[deletePriceConfig][Delete price config error][Price config not found][PriceConfigId: {}]", pcId);
      return new Response<>(0, noThatConfig, null);
    }

    Price price = op.get();
    priceConfigRepository.delete(price);
    return new Response<>(1, "Delete success", price);
  }

  @Override
  public Response<Price> updatePrice(Price c, HttpHeaders headers) {
    Optional<Price> op = priceConfigRepository.findById(c.getId());
    if (!op.isPresent()) {
      log.error("[updatePriceConfig][Update price config error][Price config not found][PriceConfigId: {}]", c.getId());
      return new Response<>(0, noThatConfig, null);
    }

    Price price = op.get();
    price.setId(c.getId());
    price.setBasicPriceRate(c.getBasicPriceRate());
    price.setFirstClassPriceRate(c.getFirstClassPriceRate());
    price.setRouteId(c.getRouteId());
    price.setTrainType(c.getTrainType());
    priceConfigRepository.save(price);
    return new Response<>(1, "Update success", price);
  }
}
