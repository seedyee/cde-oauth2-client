package io.cde.oauth2.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.cde.oauth2.client.tools.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by liaofangcai on 11/21/16.
 */
@Service
public class UserService {

  @Value("${api.emails_url}")
  private String emails_url;

  private final ParameterizedTypeReference<List<Object>> TYPE_REF_LIST_OBJECT = new ParameterizedTypeReference<List<Object>>() { };

  private URI URI_API_EMAILS;

  private final RestTemplate restTemplate = new RestTemplate();

  public List getUserEmails(final String accessToken) {
    try {
      URI_API_EMAILS = new URI(emails_url);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "token " + accessToken);
    RequestEntity<String> entity1 = new RequestEntity<>(headers, HttpMethod.GET, URI_API_EMAILS);

    ResponseEntity<List<Object>> responseEntity = restTemplate.exchange(entity1, TYPE_REF_LIST_OBJECT);

    if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
      throw new ApiException(responseEntity);
    }

    return responseEntity.getBody();
  }
}
