package io.cde.oauth2.client.agent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.cde.oauth2.client.builder.RequestInfoBuilder;
import io.cde.oauth2.client.domain.AccessTokenRequestInfo;
import io.cde.oauth2.client.domain.UserRequestInfo;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据code获取token.
 */
@Component
public class RequestAgent {

    private static final Logger logger = LoggerFactory.getLogger(RequestAgent.class);

    private static final ParameterizedTypeReference<List<Object>> requestUserInfoTypeReference = new ParameterizedTypeReference<List<Object>>() { };

    private static final ParameterizedTypeReference<Map<String, Object>> requestTokenTypeReference = new ParameterizedTypeReference<Map<String, Object>>() { };

    /**
     * 访问rest服务的客户端.
     */
    private RestTemplate restTemplate = new RestTemplate();

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
     * 根据accessTokenRequest参数entity，跟服务器交换获取token.
     * @param code 请求授权返回的code参数.
     * @return return token
     * @throws RestClientException this RestClientException
     * @throws URISyntaxException this URISyntaxException
     */
    public String getAccessToken(final String code) throws RestClientException, URISyntaxException {
        final AccessTokenRequestInfo accessTokenRequestInfo = this.requestInfoBuilder.buildAccessTokenRequestInfo(code);
        final RequestEntity<AccessTokenRequestInfo> requestEntity = new RequestEntity<>(accessTokenRequestInfo, HttpMethod.POST, new URI(this.requestAccessTokenUrl));
        final ResponseEntity<Map<String, Object>> responseEntity = this.restTemplate.exchange(requestEntity, requestTokenTypeReference);
        final Map<String, Object> responseBody = responseEntity.getBody();
        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError() || responseBody.containsKey("error")) {
            logger.error("API response entity is wrong about code");
            return null;
        }
        final String token = responseBody.get("access_token").toString();
        return token;
    }

    /**
     *
     * 根据entity获取服务器资源信息(用户相关信息).
     * @param token this token
     * @return return list
     * @throws URISyntaxException this URISyntaxException
     */
    public List<Object> getUserInfo(final String token) throws URISyntaxException {
        List<Object> list = new ArrayList<Object>();
        final HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "token " + token);
        final UserRequestInfo userRequestInfo = this.requestInfoBuilder.buildUserRequestInfo(token);
        final RequestEntity<String> entity = new RequestEntity<>(header, HttpMethod.GET, new URI(userRequestInfo.getRequestUserUrl()));
        final ResponseEntity<List<Object>> responseEntity = this.restTemplate.exchange(entity, requestUserInfoTypeReference);
        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            logger.error("API response entity is wrong about code");
            return list;
        }
        list = responseEntity.getBody();
        return list;
    }
}
