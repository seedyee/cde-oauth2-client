package io.cde.oauth2.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by liaofangcai on 11/21/16.
 */
public class AccessTokenRequest {

  private String clientId;

  private String clientSecret;

  private String code;

  public AccessTokenRequest() {}
  public AccessTokenRequest(String clientId, String clientSecret, String code) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("client_secret")
  public String getClientSecret() {
    return clientSecret;
  }
}
