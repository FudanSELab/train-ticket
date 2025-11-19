package edu.fudan.common.client;

import edu.fudan.common.client.dto.train.TrainTypeDto;
import edu.fudan.common.util.Response;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrainClient extends BaseClient {
  private static final String SERVICE_NAME = "ts-train-service";
  private static final String BASE_URL = "/api/v1/train";

  private String getServiceUrl() {
    return "http://" + SERVICE_NAME;
  }

  private String getBaseUrl() {
    return getServiceUrl() + BASE_URL;
  }

  private String getAdminBaseUrl() {
    return getBaseUrl() + "/admin";
  }

  /* ---------- public train endpoints ---------- */

  public Response<List<TrainTypeDto>> getAllTrainTypes(HttpHeaders headers) {
    return exchange(getBaseUrl() + "/trainTypes", HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<List<TrainTypeDto>>>() {});
  }

  public Response<TrainTypeDto> getTrainTypeById(String trainTypeId, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/trainTypes/" + trainTypeId, HttpMethod.GET, null, headers,
        new ParameterizedTypeReference<Response<TrainTypeDto>>() {});
  }

  public Response<TrainTypeDto> getTrainTypeByName(String trainTypeName, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/trainTypes/byName/" + trainTypeName, HttpMethod.GET, null,
        headers, new ParameterizedTypeReference<Response<TrainTypeDto>>() {});
  }

  public Response<List<TrainTypeDto>> getTrainTypesByNames(List<String> names, HttpHeaders headers) {
    return exchange(getBaseUrl() + "/trains/byNames", HttpMethod.POST, names, headers,
        new ParameterizedTypeReference<Response<List<TrainTypeDto>>>() {});
  }

  /* ---------- admin train endpoints ---------- */

  public Response<Void> adminAddTrainType(TrainTypeDto trainTypeDto, HttpHeaders headers) {
    return exchange(getAdminBaseUrl() + "/trainTypes", HttpMethod.POST, trainTypeDto, headers,
        new ParameterizedTypeReference<Response<Void>>() {});
  }

  public Response<Void> adminModifyTrainType(TrainTypeDto trainTypeDto, HttpHeaders headers) {
    return exchange(getAdminBaseUrl() + "/trainTypes", HttpMethod.PUT, trainTypeDto, headers,
        new ParameterizedTypeReference<Response<Void>>() {});
  }

  public Response<Void> adminDeleteTrainType(String trainTypeId, HttpHeaders headers) {
    return exchange(getAdminBaseUrl() + "/trainTypes/" + trainTypeId, HttpMethod.DELETE, null,
        headers, new ParameterizedTypeReference<Response<Void>>() {});
  }
}


