package io.cde.oauth2.client.domain;

/**
 * Created by liaofangcai.
 * Created time 11/29/16.
 */
public class UserRequestInfo {

    private final String token;

    private final String userRequestUrl;

    public UserRequestInfo(final String token, final String userRequestUrl) {
        this.token = token;
        this.userRequestUrl = userRequestUrl;
    }

    public String getRequestUserUrl() {
        return userRequestUrl;
    }

    public String getToken() {
        return token;
    }
}
