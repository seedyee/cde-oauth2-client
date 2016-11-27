package io.cde.oauth2.api.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

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
import org.springframework.web.client.RestTemplate;

import io.cde.oauth2.api.client.build.RequestBuild;
import io.cde.oauth2.api.client.domain.AccessTokenRequest;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据code获取token.
 */
@Service
public class AccessTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);

    private static final ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<Map<String, Object>>() { };

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取请求code的url的参数列表.
     */
    @Autowired
    private RequestBuild requestBuild;

    /**
     * 请求token的服务器url.
     */
    @Value("${io.cde.oauth2.api.client.requestAccessTokenUrl}")
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
        final AccessTokenRequest accessTokenRequest = this.requestBuild.getAccessTokenRequest(code);
        final RequestEntity<AccessTokenRequest> requestEntity = new RequestEntity<>(accessTokenRequest, HttpMethod.POST, new URI(this.requestAccessTokenUrl));
        final ResponseEntity<Map<String, Object>> responseEntity = this.restTemplate.exchange(requestEntity, typeReference);
        final Map<String, Object> responseBody = responseEntity.getBody();
        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError() || responseBody.containsKey("error")) {
            logger.error("API response entity is wrong about code");
            return null;
        }
        final String accessToken = responseBody.get("access_token").toString();
        return accessToken;
    }
}
