package io.cde.oauth2.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by liaofangcai on 11/22/16.
 */
@Configuration
public class UriComponentsBuilderBean {

  @Value("${api.client_id}")
  private String clientId;

  @Value("${api.scope}")
  private String scope;

  @Value("${api.redirect_uri}")
  private String redirectUri;

  @Value("${api.authorize_uri}")
  private String authorize_uri;

  @Bean(name = "uriComponentsBuilder")
  public UriComponentsBuilder getUriComponentsBuilder() {
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(authorize_uri);
    uriComponentsBuilder.queryParam("client_id", clientId);
    uriComponentsBuilder.queryParam("scope", scope);
    uriComponentsBuilder.queryParam("redirect_uri", redirectUri);

    return uriComponentsBuilder;
  }
}
