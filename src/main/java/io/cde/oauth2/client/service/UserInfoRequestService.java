package io.cde.oauth2.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.cde.oauth2.client.agent.RequestAgent;
import io.cde.oauth2.client.builder.RequestInfoBuilder;
import io.cde.oauth2.client.domain.UserRequestInfo;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据token请求用户数据.
 */
@Service
public class UserInfoRequestService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoRequestService.class);

    private static final ParameterizedTypeReference<List<Object>> typeReference = new ParameterizedTypeReference<List<Object>>() { };

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
     *
     * 根据token获取服务器资源信息(用户相关信息).
     * @param token 服务器获取的token令牌密匙.
     * @return 用户相关信息数据.
     * @throws URISyntaxException this URISyntaxException
     */
    public List<Object> getUserWithAccessToken(final String token) throws URISyntaxException {
        List<Object> list = new ArrayList<Object>();
        final HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "token " + token);
        final UserRequestInfo userRequestInfo = this.requestInfoBuilder.buildUserRequestInfo(token);
        final RequestEntity<String> entity = new RequestEntity<>(header, HttpMethod.GET, new URI(userRequestInfo.getRequestUserUrl()));

        final ResponseEntity<List<Object>> responseEntity = this.agent.getUserInfo(entity, typeReference);

        if (responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            logger.error("API response entity is wrong about code");
            return list;
        }
        list = responseEntity.getBody();
        return list;
    }
}
