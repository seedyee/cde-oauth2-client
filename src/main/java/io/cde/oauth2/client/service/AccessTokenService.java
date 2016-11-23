package io.cde.oauth2.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import io.cde.oauth2.client.tools.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.cde.oauth2.client.domain.AccessTokenRequest;

/**
 * Created by liaofangcai on 11/21/16.
 */
@Service
public class AccessTokenService {

  private final ParameterizedTypeReference<Map<String, String>> TYPE_REF_MAP_STRING_STRING = new ParameterizedTypeReference<Map<String, String>>() { };

  @Value("${api.client_id}")
  private String clientId;

  @Value("${api.client_secret}")
  private String clientSecret;

  @Value("${api.access_token_url}")
  private String access_token_url;

  private RestTemplate restTemplate = new RestTemplate();

  public String getAccessToken(final String code) throws URISyntaxException {

    AccessTokenRequest accessTokenRequest = new AccessTokenRequest(clientId,clientSecret, code);
    final RequestEntity<AccessTokenRequest> requestEntity = new RequestEntity<>(accessTokenRequest, HttpMethod.POST, new URI(access_token_url));
    final ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(requestEntity, TYPE_REF_MAP_STRING_STRING);

    final Map<String, String> responseBody = responseEntity.getBody();
    if (responseEntity.getStatusCode().is4xxClientError() ||
        responseEntity.getStatusCode().is5xxServerError() ||
        responseBody.containsKey("error")) {
      throw new ApiException(responseEntity);
    }

    return responseBody.get("access_token");
  }
}
