package io.cde.oauth2.client.tools;

import org.springframework.http.ResponseEntity;

/**
 * Created by liaofangcai on 11/21/16.
 */
public class ApiException extends RuntimeException {

  private final ResponseEntity<?> responseEntity;

  public ApiException(ResponseEntity<?> responseEntity) {
    this.responseEntity = responseEntity;
  }

  @Override
  public String getMessage() {
    return "API response entity: [" + responseEntity + "]";
  }
}
