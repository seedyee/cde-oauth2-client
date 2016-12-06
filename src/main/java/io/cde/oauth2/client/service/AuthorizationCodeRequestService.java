package io.cde.oauth2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.client.builder.RequestInfoBuilder;
import io.cde.oauth2.client.domain.AuthorizationCodeRequestInfo;

/**
 * Created by liaofangcai.
 * Created time 11/29/16.
 */
@Service
public class AuthorizationCodeRequestService {

    /**
     * 记录日志.
     */
    private final Logger logger = LoggerFactory.getLogger(AuthorizationCodeRequestService.class);

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
}
