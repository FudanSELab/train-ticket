package price.controller;

import edu.fudan.common.client.dto.ticket.PriceDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import price.mapper.PriceMapper;
import price.service.PriceService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/ticket")
public class TicketController {
  @Autowired
  PriceService priceService;
  @Autowired
  PriceMapper priceMapper;

  @GetMapping(path = "/prices/welcome")
  public String home() {
    return "Welcome to [ Price Service ] !";
  }

  @GetMapping(value = "/prices/{routeId}/{trainType}")
  public HttpEntity<Response<PriceDto>> query(@PathVariable String routeId, @PathVariable String trainType,
      @RequestHeader HttpHeaders headers) {
    log.info("[Query price by route and train type][RouteId: {}, TrainType: {}]", routeId, trainType);
    return ok(priceMapper.toDtoResponse(priceService.findByRouteIdAndTrainType(routeId, trainType, headers)));
  }

  @PostMapping(value = "/prices/byRouteIdsAndTrainTypes")
  public HttpEntity<Response<Map<String, PriceDto>>> query(@RequestBody List<String> ridsAndTts,
      @RequestHeader HttpHeaders headers) {
    log.info("[Query prices by route and train type][routeId and Train Type: {}]", ridsAndTts);
    return ok(priceMapper.toDtoMapResponse(priceService.findByRouteIdsAndTrainTypes(ridsAndTts, headers)));
  }

  @GetMapping(value = "/prices")
  public HttpEntity<Response<List<PriceDto>>> queryAll(@RequestHeader HttpHeaders headers) {
    log.info("[Query all prices]");
    return ok(priceMapper.toDtoListResponse(priceService.findAllPrice(headers)));
  }
}
