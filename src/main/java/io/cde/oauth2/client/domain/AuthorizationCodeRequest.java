package io.cde.oauth2.client.domain;

/**
 * Created by liaofangcai.
 * Created time 11/28/16.
 */
public class AuthorizationCodeRequest {

    private final String clientId;

    private final String scope;

    private final String redirectUrl;

    private final long state;

    private final String responseType;

    private final String requestAuthorizeUrl;

    public AuthorizationCodeRequest(final String clientId, final String scope, final String redirectUrl, final long state, final String responseType, final String requestAuthorizeUrl) {
        this.clientId = clientId;
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

    public String getClientId() {
        return clientId;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getRequestAuthorizeUrl() {
        return requestAuthorizeUrl;
    }
}
