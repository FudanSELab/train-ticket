package edu.fudan.common.client;

import edu.fudan.common.client.dto.ticket.PriceDto;
import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Client for interacting with the Order Service.
 */
@Slf4j
@Component
public class TicketClient extends BaseClient {
  private static final String SERVICE_NAME = "ts-ticket-service";
  private static final String BASE_URL = "/api/v1/ticket";

  private String getServiceUrl() {
    return "http://" + SERVICE_NAME;
  }

  private String getBaseUrl() {
    return getServiceUrl() + BASE_URL;
  }

  /* ---------- public price endpoints ---------- */

  public Response<PriceDto> getPriceByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/prices/" + routeId + "/" + trainType, HttpMethod.GET,
        null, headers, new ParameterizedTypeReference<Response<PriceDto>>() {
        });
  }

  public Response<Map<String, PriceDto>> getPricesByRouteIdsAndTrainTypes(List<String> ridsAndTts,
      HttpHeaders headers) {
    return exchange(getBaseUrl() + "/prices/byRouteIdsAndTrainTypes", HttpMethod.POST,
        ridsAndTts, headers, new ParameterizedTypeReference<Response<Map<String, PriceDto>>>() {
        });
  }

  public Response<List<PriceDto>> getAllPrices(HttpHeaders headers) {
    return exchange(getBaseUrl() + "/prices", HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<List<PriceDto>>>() {
        });
  }

  /* ---------- admin price endpoints ---------- */

  public Response<PriceDto> adminAddPrice(PriceDto priceDto, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/prices", HttpMethod.POST, priceDto, headers,
        new ParameterizedTypeReference<Response<PriceDto>>() {
        });
  }

  public Response<PriceDto> adminModifyPrice(PriceDto priceDto, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/prices", HttpMethod.PUT, priceDto, headers,
        new ParameterizedTypeReference<Response<PriceDto>>() {
        });
  }

  public Response<PriceDto> adminDeletePrice(String priceId, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/prices/" + priceId, HttpMethod.DELETE, null, headers,
        new ParameterizedTypeReference<Response<PriceDto>>() {
        });
  }
}
