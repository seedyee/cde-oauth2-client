package io.cde.oauth2.client.build;

import java.security.SecureRandom;

import io.cde.oauth2.client.domain.AccessTokenRequest;
import io.cde.oauth2.client.domain.AuthorizationCodeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liaofangcai.
 * Created time 11/23/16.
 */
@Component
public class RequestBuilder {

    /**
     * 项目注册id.
     */
    @Value("${cde.oauth2.client.clientId}")
    private String clientId;

    /**
     * 请求的授权.
     */
    @Value("${cde.oauth2.client.scope}")
    private String scope;

    /**
     * 根据token请求数据之后的回调url.
     */
    @Value("${cde.oauth2.client.redirectUrl}")
    private String redirectUrl;

    /**
     * 获取code的请求授权url.
     */
    @Value("${cde.oauth2.client.requestAuthorizeUrl}")
    private String requestAuthorizeUrl;

    /**
     * 项目注册的Client_Secret.
     */
    @Value("${cde.oauth2.client.clientSecret}")
    private String clientSecret;

    /**
     * 表示授权类型.
     */
    private final String responseType = "code";

    /**
     * 使用的授权模式.
     */
    private final String grantType = "authorization_code";

    /**
     * 获取AccessTokenRequest.
     * @param code this code
     * @return this AccessTokenRequest
     */
    public AccessTokenRequest getAccessTokenRequest(final String code) {
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(this.clientId, this.clientSecret, code, this.grantType);
        return accessTokenRequest;
    }

    /**
     * 获取CodeRequest.
     * @return this CodeRequest
     */
    public AuthorizationCodeRequest getCodeRequestUrl() {
        final long state = new SecureRandom().nextLong();
        final AuthorizationCodeRequest authorizationCodeRequest = new AuthorizationCodeRequest(this.clientId, this.scope, this.redirectUrl, state, this.responseType, this.requestAuthorizeUrl);
        return authorizationCodeRequest;
    }
}
