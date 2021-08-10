package price.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import price.entity.PriceConfig;
import price.service.PriceService;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/priceservice")
public class PriceController {

    @Autowired
    PriceService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    @GetMapping(path = "/prices/welcome")
    public String home() {
        return "Welcome to [ Price Service ] !";
    }

    @GetMapping(value = "/prices/{routeId}/{trainType}")
    public HttpEntity query(@PathVariable String routeId, @PathVariable String trainType,
                            @RequestHeader HttpHeaders headers) {
        PriceController.LOGGER.info("Query price, RouteId: {}, TrainType: {}",routeId,trainType);
        return ok(service.findByRouteIdAndTrainType(routeId, trainType, headers));
    }

    @GetMapping(value = "/prices")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        PriceController.LOGGER.info("Query all prices");
        return ok(service.findAllPriceConfig(headers));
    }

    @PostMapping(value = "/prices")
    public HttpEntity<?> create(@RequestBody PriceConfig info,
                                @RequestHeader HttpHeaders headers) {
        PriceController.LOGGER.info("Create price, RouteId: {}, TrainType: {}",info.getRouteId(),info.getTrainType());
        return new ResponseEntity<>(service.createNewPriceConfig(info, headers), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/prices")
    public HttpEntity delete(@RequestBody PriceConfig info, @RequestHeader HttpHeaders headers) {
        PriceController.LOGGER.info("Delete price, PriceConfigId: {}",info.getId());
        return ok(service.deletePriceConfig(info, headers));
    }

    @PutMapping(value = "/prices")
    public HttpEntity update(@RequestBody PriceConfig info, @RequestHeader HttpHeaders headers) {
        PriceController.LOGGER.info("Update price, PriceConfigId: {}",info.getId());
        return ok(service.updatePriceConfig(info, headers));
    }
}
