package io.cde.oauth2.client.service;

import io.cde.oauth2.client.agent.RequestAgent;
import io.cde.oauth2.client.builder.RequestInfoBuilder;
import io.cde.oauth2.client.domain.AuthorizationCodeRequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaofangcai.
 * Created time 12/7/16.
 */
@Service
public class OAuthService {

    /**
     * 记录日志.
     */
    private static final Logger logger = LoggerFactory.getLogger(OAuthService.class);

    /**
     * 获取请求第三方api而封装的agent.
     */
    @Autowired
    private RequestAgent agent;

    /**
     * 获取请求code的url的参数列表.
     */
    @Autowired
    private RequestInfoBuilder requestInfoBuilder;

    /**
     * 组装请求code的url.
     * @return url
     */
    public String getRequestCodeUrl() {
        logger.info("authentication start");
        final AuthorizationCodeRequestInfo authorizationCodeRequestInfo = requestInfoBuilder.buildAuthorizationCodeRequestInfo();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(authorizationCodeRequestInfo.getRequestAuthorizeUrl());
        uriComponentsBuilder.queryParam("client_id", authorizationCodeRequestInfo.getClientId());
        uriComponentsBuilder.queryParam("scope", authorizationCodeRequestInfo.getScope());
        uriComponentsBuilder.queryParam("redirect_uri", authorizationCodeRequestInfo.getRedirectUrl());
        uriComponentsBuilder.queryParam("state", authorizationCodeRequestInfo.getState());
        uriComponentsBuilder.queryParam("response_type", authorizationCodeRequestInfo.getResponseType());
        return uriComponentsBuilder.toUriString();
    }

    /**
     *
     * 根据accessTokenRequest参数entity，跟服务器交换获取token.
     * @param code 请求授权返回的code参数.
     * @return 返回token
     * @throws RestClientException this RestClientException
     * @throws URISyntaxException this URISyntaxException
     */
    public String getAccessTokenByCode(final String code) throws RestClientException, URISyntaxException {
        logger.info("get token by code");
        final String token;
        token = this.agent.getAccessToken(code);
        return token;
    }

    /**
     *
     * 根据token获取服务器资源信息(用户相关信息).
     * @param token 服务器获取的token令牌密匙.
     * @return 用户相关信息数据.
     * @throws URISyntaxException this URISyntaxException
     */
    public List<Object> getUserWithAccessToken(final String token) throws URISyntaxException {
        logger.info("get user information according to token");
        List<Object> list = new ArrayList<Object>();
        list = this.agent.getUserInfo(token);
        return list;
    }
}
