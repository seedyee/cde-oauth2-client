package io.cde.oauth2.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserInfoRequestService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoRequestService.class);

    private static final ParameterizedTypeReference<List<Object>> typeReferenc = new ParameterizedTypeReference<List<Object>>() { };

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 向服务器请求资源的url.
     */
    @Value("${cde.oauth2.client.requestUserUrl}")
    private String requestUserInfoUrl;

    /**
     *
     * 根据token获取服务器资源信息(用户相关信息).
     * @param accessToken 服务器获取的token令牌密匙.
     * @return 用户相关信息数据.
     * @throws URISyntaxException this URISyntaxException
     */
    public List<Object> getUserByAccessToken(final String accessToken) throws URISyntaxException {
        List<Object> list = new ArrayList<Object>();
        final HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "token " + accessToken);
        final URI uri = new URI(this.requestUserInfoUrl);
        final RequestEntity<String> entity = new RequestEntity<>(header, HttpMethod.GET, uri);
        final ResponseEntity<List<Object>> responseEntity = this.restTemplate.exchange(entity, typeReferenc);
        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            logger.error("API response entity is wrong about code");
            return list;
        }
        list = responseEntity.getBody();
        return list;
    }
}
