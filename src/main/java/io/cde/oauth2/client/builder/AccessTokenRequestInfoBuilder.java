package io.cde.oauth2.client.builder;

import io.cde.oauth2.client.domain.AccessTokenRequestInfo;

/**
 * Created by liaofangcai.
 * Created time 12/8/16.
 */
public class AccessTokenRequestInfoBuilder {

    private String clientSecret;

    private String code;

    private String grantType;

    private String clientId;

    public AccessTokenRequestInfoBuilder setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public AccessTokenRequestInfoBuilder setCode(final String code) {
        this.code = code;
        return this;
    }

    public AccessTokenRequestInfoBuilder setGrantType(final String grantType) {
        this.grantType = grantType;
        return this;
    }

    public AccessTokenRequestInfoBuilder setClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    public AccessTokenRequestInfo build() {
        return new AccessTokenRequestInfo(this.clientId, this.clientSecret, code, this.grantType);
    }
}
