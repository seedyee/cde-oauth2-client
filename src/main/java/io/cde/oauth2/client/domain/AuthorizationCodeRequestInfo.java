package io.cde.oauth2.client.domain;

import io.cde.oauth2.client.domain.base.BaseRequestInfo;

/**
 * Created by liaofangcai.
 * Created time 11/28/16.
 */
public class AuthorizationCodeRequestInfo extends BaseRequestInfo {

    private final String scope;

    private final String redirectUrl;

    private final long state;

    private final String responseType;

    private final String requestAuthorizeUrl;

    public AuthorizationCodeRequestInfo(final String clientId, final String scope, final String redirectUrl, final long state, final String responseType, final String requestAuthorizeUrl) {
        super(clientId);
        this.scope = scope;
        this.redirectUrl = redirectUrl;
        this.state = state;
        this.responseType = responseType;
        this.requestAuthorizeUrl = requestAuthorizeUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public long getState() {
        return state;
    }

    public String getScope() {
        return scope;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getRequestAuthorizeUrl() {
        return requestAuthorizeUrl;
    }
}
