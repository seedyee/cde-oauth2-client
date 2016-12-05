package io.cde.oauth2.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.cde.oauth2.client.domain.base.BaseRequestInfo;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求token的entity.
 */
public class AccessTokenRequestInfo extends BaseRequestInfo {

    private final String clientSecret;

    private final String code;

    private final String grantType;

    public AccessTokenRequestInfo(final String clientId, final String clientSecret, final String code, final String grantType) {
        super(clientId);
        this.clientSecret = clientSecret;
        this.code = code;
        this.grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    @JsonProperty("grant_type")
    public String getGrantType() {
        return grantType;
    }
}
