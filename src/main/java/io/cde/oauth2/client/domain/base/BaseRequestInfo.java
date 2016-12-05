package io.cde.oauth2.client.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by liaofangcai.
 * Created time 11/30/16.
 */
public class BaseRequestInfo {

    private final String clientId;

    public BaseRequestInfo(final  String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }
}
