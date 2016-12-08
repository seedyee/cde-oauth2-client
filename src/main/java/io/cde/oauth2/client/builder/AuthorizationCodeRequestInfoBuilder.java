package io.cde.oauth2.client.builder;

import io.cde.oauth2.client.domain.AuthorizationCodeRequestInfo;

/**
 * Created by liaofangcai.
 * Created time 12/8/16.
 */
public class AuthorizationCodeRequestInfoBuilder {

    private String scope;

    private String redirectUrl;

    private long state;

    private String responseType;

    private String requestAuthorizeUrl;

    private String clientId;

    public AuthorizationCodeRequestInfoBuilder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public AuthorizationCodeRequestInfoBuilder setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public AuthorizationCodeRequestInfoBuilder setRequestAuthorizeUrl(String requestAuthorizeUrl) {
        this.requestAuthorizeUrl = requestAuthorizeUrl;
        return this;
    }

    public AuthorizationCodeRequestInfoBuilder setResponseType(String responseType) {
        this.responseType = responseType;
        return this;
    }

    public AuthorizationCodeRequestInfoBuilder setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public AuthorizationCodeRequestInfoBuilder setState(long state) {
        this.state = state;
        return this;
    }

    public AuthorizationCodeRequestInfo build() {
        return new AuthorizationCodeRequestInfo(this.clientId, this.scope, this.redirectUrl, state, this.responseType, this.requestAuthorizeUrl);
    }

}
