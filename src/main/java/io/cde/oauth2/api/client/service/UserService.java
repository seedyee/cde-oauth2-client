package io.cde.oauth2.api.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.cde.oauth2.api.client.lang.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据token请求用户数据.
 */
@Service
public class UserService {

    private final ParameterizedTypeReference<List<Object>> typeReferenc = new ParameterizedTypeReference<List<Object>>() { };

    private final HttpHeaders headers = new HttpHeaders();

    private URI url = null;

    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * 向服务器请求资源的url.
     */
    @Value("${oauth2.api.client.requestUserInfoUrl}")
    private String requestUserInfoUrl;
    /**
     * 根据token获取服务器资源信息(用户相关信息).
     * @param accessToken 服务器获取的token令牌密匙.
     * @return 用户相关信息数据.
     */
    public List getUserInforByAccessToken(final String accessToken) {
        try {
            url = new URI(requestUserInfoUrl);
            headers.set("Authorization", "token " + accessToken);
            final RequestEntity<String> entity1 = new RequestEntity<>(headers, HttpMethod.GET, url);
            final ResponseEntity<List<Object>> responseEntity = restTemplate.exchange(entity1, typeReferenc);
            if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
                throw new ApiException(responseEntity);
            }
            return responseEntity.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
