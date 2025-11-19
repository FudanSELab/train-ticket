package edu.fudan.common.client;

import edu.fudan.common.client.dto.station.StationDto;
import edu.fudan.common.util.Response;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StationClient extends BaseClient {
  private static final String SERVICE_NAME = "ts-station-service";
  private static final String BASE_URL = "/api/v1/station";

  private String getServiceUrl() {
    return "http://" + SERVICE_NAME;
  }

  private String getBaseUrl() {
    return getServiceUrl() + BASE_URL;
  }

  /* ---------- public station endpoints ---------- */

  public Response<List<StationDto>> getAllStations(HttpHeaders headers) {
    return exchange(getBaseUrl() + "/stations", HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<List<StationDto>>>() {});
  }

  public Response<String> getStationIdByName(String stationName, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/stations/id/" + stationName, HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<String>>() {});
  }

  public Response<Map<String, String>> getStationIdsByNames(List<String> stationNames,
      HttpHeaders headers) {
    return exchange(getBaseUrl() + "/stations/idlist", HttpMethod.POST, stationNames, headers,
        new ParameterizedTypeReference<Response<Map<String, String>>>() {});
  }

  public Response<String> getStationNameById(String stationId, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/stations/name/" + stationId, HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<String>>() {});
  }

  public Response<List<String>> getStationNamesByIds(List<String> stationIds, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/stations/namelist", HttpMethod.POST, stationIds, headers,
        new ParameterizedTypeReference<Response<List<String>>>() {});
  }

  /* ---------- admin station endpoints ---------- */

  public Response<StationDto> adminCreateStation(StationDto stationDto, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/stations", HttpMethod.POST, stationDto, headers,
        new ParameterizedTypeReference<Response<StationDto>>() {});
  }

  public Response<StationDto> adminUpdateStation(StationDto stationDto, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/stations", HttpMethod.PUT, stationDto, headers,
        new ParameterizedTypeReference<Response<StationDto>>() {});
  }

  public Response<StationDto> adminDeleteStation(String stationId, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/admin/stations/" + stationId, HttpMethod.DELETE, null, headers,
        new ParameterizedTypeReference<Response<StationDto>>() {});
  }
}

