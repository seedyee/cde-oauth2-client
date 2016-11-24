package io.cde.oauth2.api.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.cde.oauth2.api.client.domain.AccessTokenRequest;
import io.cde.oauth2.api.client.lang.ApiException;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据code获取token.
 */
@Service
public class AccessTokenService {

    private final ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<Map<String, String>>() { };

    private RestTemplate restTemplate = new RestTemplate();
    /**
     * 请求token的服务器url.
     */
    @Value("${oauth2.api.client.requestAccessTokenUrl}")
    private String requestAccessTokenUrl;
    /**
     * 根据code请求token的entity.
     */
    @Autowired
    private AccessTokenRequest accessTokenRequest;
    /**
     * 根据accessTokenRequest参数entity，跟服务器交换获取token.
     * @param code 请求授权返回的code参数.
     * @return 返回token.
     */
    public String getAccessTokenByCode(final String code) throws URISyntaxException {
        accessTokenRequest.setCode(code);
        final RequestEntity<AccessTokenRequest> requestEntity = new RequestEntity<>(accessTokenRequest, HttpMethod.POST, new URI(requestAccessTokenUrl));
        try {
            final ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(requestEntity, typeReference);
            final Map<String, String> responseBody = responseEntity.getBody();
            if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError() || responseBody.containsKey("error")) {
                throw new ApiException(responseEntity);
            }
            return responseBody.get("access_token");
        } catch (RestClientException restClientException) {
            throw restClientException;
        }
    }
}
