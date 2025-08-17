package price.controller;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import price.entity.PriceConfig;
import price.service.PriceService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/priceservice/admin")
public class AdminPriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPriceController.class);

    @Autowired
    private PriceService priceService;

    @GetMapping("/welcome")
    public String home(@RequestHeader HttpHeaders headers){
        return "Welcome to [ Admin Price Service ] !";
    }

    @GetMapping("/prices")
    public ResponseEntity<Response> getAllPrices(@RequestHeader HttpHeaders headers){
        LOGGER.info("[getAllPrices][Admin get all price configs]");
        return ok(priceService.findAllPriceConfig(headers));
    }

    @PostMapping("/prices")
    public ResponseEntity<Response> addPrice(@RequestBody PriceConfig config,@RequestHeader HttpHeaders headers){
        LOGGER.info("[addPrice][Admin add price][id: {}]",config.getId());
        return new ResponseEntity<>(priceService.createNewPriceConfig(config, headers), HttpStatus.CREATED);
    }

    @PutMapping("/prices")
    public ResponseEntity<Response> modifyPrice(@RequestBody PriceConfig config,@RequestHeader HttpHeaders headers){
        LOGGER.info("[modifyPrice][Admin modify price][id: {}]",config.getId());
        return ok(priceService.updatePriceConfig(config, headers));
    }

    @DeleteMapping("/prices/{priceId}")
    public ResponseEntity<Response> deletePrice(@PathVariable String priceId,@RequestHeader HttpHeaders headers){
        LOGGER.info("[deletePrice][Admin delete price][id: {}]",priceId);
        return ok(priceService.deletePriceConfig(priceId, headers));
    }
}
