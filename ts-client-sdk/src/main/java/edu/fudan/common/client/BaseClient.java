package edu.fudan.common.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import edu.fudan.common.util.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseClient {
  @Autowired
  protected RestTemplate restTemplate;

  protected <T> Response<T> exchange(String url, HttpMethod method, Object body, HttpHeaders headers,
      ParameterizedTypeReference<Response<T>> type) {
    log.info("[BaseClient][exchange][url: {}, method: {}]", url, method);
    HttpEntity<?> entity = body == null ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers);
    ResponseEntity<Response<T>> response = restTemplate.exchange(url, method, entity, type);
    if (response.getBody() != null) {
      log.info("[BaseClient][exchange][response status: {}, msg: {}]", response.getBody().getStatus(),
          response.getBody().getMsg());
    }
    return response.getBody();
  }
}
