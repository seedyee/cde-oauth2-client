package io.cde.oauth2.api.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.api.client.domain.AccessTokenRequest;

/**
 * Created by liaofangcai.
 * Created time 11/23/16.
 */
@Configuration
public class RequestConfiguration {

    /**
     * 项目注册id.
     */
    @Value("${oauth2.api.client.clientId}")
    private String clientId;
    /**
     * 请求的授权.
     */
    @Value("${oauth2.api.client.scope}")
    private String scope;
    /**
     * 根据token请求数据之后的回调url.
     */
    @Value("${oauth2.api.client.redirectUrl}")
    private String redirectUrl;
    /**
     * 获取code的请求授权url.
     */
    @Value("${oauth2.api.client.requestAuthorizeUrl}")
    private String requestAuthorizeUrl;
    /**
     * 项目注册的Client_Secret.
     */
    @Value("${oauth2.api.client.clientSecret}")
    private String clientSecret;
    /**
     * 用户授权请求回调获取的code, 默认为空.
     */
    private String code = null;
    /**
     * 根据项目注册的参数，组装请求code的entity.
     * @return UriComponentsBuilder.
     */
    @Bean
    public UriComponentsBuilder getUriComponentsBuilder() {
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestAuthorizeUrl);
        uriComponentsBuilder.queryParam("client_id", clientId);
        uriComponentsBuilder.queryParam("scope", scope);
        uriComponentsBuilder.queryParam("redirect_uri", redirectUrl);
        return uriComponentsBuilder;
    }
    /**
     * 根据项目注册参数，组装请求token的entity.
     * @return AccessTokenRequest.
     */
    @Bean
    public AccessTokenRequest getAccessTokenRequest() {
        final AccessTokenRequest accessTokenRequest = new AccessTokenRequest(clientId, clientSecret, code);
        return accessTokenRequest;
    }
}
