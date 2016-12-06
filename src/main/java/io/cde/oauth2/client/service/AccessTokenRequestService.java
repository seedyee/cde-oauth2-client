package io.cde.oauth2.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import io.cde.oauth2.client.domain.AccessTokenRequestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import io.cde.oauth2.client.agent.RequestAgent;
import io.cde.oauth2.client.builder.RequestInfoBuilder;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据code获取token.
 */
@Service
public class AccessTokenRequestService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenRequestService.class);

    private static final ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<Map<String, Object>>() { };

    /**
     * 获取请求第三方api而封装的agent.
     */
    @Autowired
    private RequestAgent agent;

    /**
     * 获取请求参数对象的build.
     */
    @Autowired
    private RequestInfoBuilder requestInfoBuilder;

    /**
     * 请求token的服务器url.
     */
    @Value("${cde.oauth2.client.requestAccessTokenUrl}")
    private String requestAccessTokenUrl;

    /**
     *
     * 根据accessTokenRequest参数entity，跟服务器交换获取token.
     * @param code 请求授权返回的code参数.
     * @return 返回token
     * @throws RestClientException this RestClientException
     * @throws URISyntaxException this URISyntaxException
     */
    public String getAccessTokenByCode(final String code) throws RestClientException, URISyntaxException {
        final AccessTokenRequestInfo accessTokenRequestInfo = this.requestInfoBuilder.buildAccessTokenRequestInfo (code);
        final RequestEntity<AccessTokenRequestInfo> requestEntity = new RequestEntity<>(accessTokenRequestInfo, HttpMethod.POST, new URI(this.requestAccessTokenUrl));

        final ResponseEntity<Map<String, Object>> responseEntity = this.agent.getAccessToken(requestEntity, typeReference);
        final Map<String, Object> responseBody = responseEntity.getBody();
        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError() || responseBody.containsKey("error")) {
            logger.error("API response entity is wrong about code");
            return null;
        }
        final String accessToken = responseBody.get("access_token").toString();
        return accessToken;
    }
}
