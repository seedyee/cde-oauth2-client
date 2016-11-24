package io.cde.oauth2.api.client.domain;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求token的entity.
 */
public class AccessTokenRequest {

    private String client_id;

    private String client_secret;

    private String code;

    public AccessTokenRequest(final String clientId, final String clientSecret, final String code) {
        this.client_id = clientId;
        this.client_secret = clientSecret;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }
}
