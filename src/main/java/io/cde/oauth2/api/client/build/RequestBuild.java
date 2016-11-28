package io.cde.oauth2.api.client.build;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.api.client.domain.AccessTokenRequest;

/**
 * Created by liaofangcai.
 * Created time 11/23/16.
 */
@Component
public class RequestBuild {

    /**
     * 项目注册id.
     */
    @Value("${io.cde.oauth2.api.client.clientId}")
    private String clientId;

    /**
     * 请求的授权.
     */
    @Value("${io.cde.oauth2.api.client.scope}")
    private String scope;

    /**
     * 根据token请求数据之后的回调url.
     */
    @Value("${io.cde.oauth2.api.client.redirectUrl}")
    private String redirectUrl;

    /**
     * 获取code的请求授权url.
     */
    @Value("${io.cde.oauth2.api.client.requestAuthorizeUrl}")
    private String requestAuthorizeUrl;

    /**
     * 项目注册的Client_Secret.
     */
    @Value("${io.cde.oauth2.api.client.clientSecret}")
    private String clientSecret;

    /**
     * 获取AccessTokenRequest.
     * @param code this code
     * @return this AccessTokenRequest
     */
    public AccessTokenRequest getAccessTokenRequest(final String code) {
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(this.clientId, this.clientSecret, code);
        return accessTokenRequest;
    }

    /**
     * 获取CodeRequest.
     * @return this CodeRequest
     */
    public UriComponentsBuilder getCodeRequestUrlBuild() {
        final long state = new SecureRandom().nextLong();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(this.requestAuthorizeUrl);
        uriComponentsBuilder.queryParam("client_id", this.clientId);
        uriComponentsBuilder.queryParam("scope", this.scope);
        uriComponentsBuilder.queryParam("redirect_uri", this.redirectUrl);
        uriComponentsBuilder.queryParam("state", state);
        return uriComponentsBuilder;
    }
}
