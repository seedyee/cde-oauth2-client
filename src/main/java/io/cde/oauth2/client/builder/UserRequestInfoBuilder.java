package io.cde.oauth2.client.builder;

import io.cde.oauth2.client.domain.UserRequestInfo;

/**
 * Created by liaofangcai.
 * Created time 12/8/16.
 */
public class UserRequestInfoBuilder {

    private String token;

    private String userRequestUrl;

    public UserRequestInfoBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public UserRequestInfoBuilder setUserRequestUrl(String userRequestUrl) {
        this.userRequestUrl = userRequestUrl;
        return this;
    }

    public UserRequestInfo build() {
        return new UserRequestInfo(this.token, this.userRequestUrl);
    }
}
