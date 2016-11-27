package io.cde.oauth2.api.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求token的entity.
 */
public class AccessTokenRequest {

    private String clientId;

    private String clientSecret;

    private String code;

    public AccessTokenRequest(final String clientId, final String clientSecret, final String code) {
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
