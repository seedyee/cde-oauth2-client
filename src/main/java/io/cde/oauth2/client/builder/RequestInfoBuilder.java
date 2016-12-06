package io.cde.oauth2.client.builder;

import java.security.SecureRandom;

import io.cde.oauth2.client.domain.AccessTokenRequestInfo;
import io.cde.oauth2.client.domain.AuthorizationCodeRequestInfo;
import io.cde.oauth2.client.domain.UserRequestInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liaofangcai.
 * Created time 11/23/16.
 */
@Component
public class RequestInfoBuilder {

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
     * 请求授权登录获取code数据之后的回调url.
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
     * 向服务器请求资源的url.
     */
    @Value("${cde.oauth2.client.requestUserUrl}")
    private String userRequestUrl;

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
     * @return this AccessTokenRequestInfo
     */
    public AccessTokenRequestInfo buildAccessTokenRequestInfo(final String code) {
        final AccessTokenRequestInfo accessTokenRequestInfo = new AccessTokenRequestInfo (this.clientId, this.clientSecret, code, this.grantType);
        return accessTokenRequestInfo;
    }

    /**
     * 获取CodeRequest.
     * @return this CodeRequest
     */
    public AuthorizationCodeRequestInfo buildAuthorizationCodeRequestInfo() {
        final long state = new SecureRandom().nextLong();
        final AuthorizationCodeRequestInfo authorizationCodeRequestInfo = new AuthorizationCodeRequestInfo(this.clientId, this.scope, this.redirectUrl, state, this.responseType, this.requestAuthorizeUrl);
        return authorizationCodeRequestInfo;
    }

    public UserRequestInfo buildUserRequestInfo(final String token) {
        final UserRequestInfo userRequestInfo = new UserRequestInfo(token, this.userRequestUrl);
        return  userRequestInfo;
    }
}