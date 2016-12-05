package io.cde.oauth2.client.agent;

import java.util.List;
import java.util.Map;

import io.cde.oauth2.client.domain.AccessTokenRequestInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by liaofangcai on 11/21/16.
 * 根据code获取token.
 */
@Component
public class RequestAgent {

    /**
     * 访问rest服务的客户端.
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 根据accessTokenRequest参数entity，跟服务器交换获取token.
     * @param requestEntity 请求授权返回的code参数.
     * @param typeReference 组装返回参数
     * @return return responseEntity
     */
    public ResponseEntity<Map<String, Object>> getAccessToken(final RequestEntity<AccessTokenRequestInfo> requestEntity, final ParameterizedTypeReference<Map<String, Object>> typeReference) {
        return this.restTemplate.exchange(requestEntity, typeReference);
    }

    /**
     * 根据entity获取服务器资源信息(用户相关信息).
     * @param requestEntity 组装的请求数据和url
     * @param typeReference 组装返回参数
     * @return return responseEntity
     */
    public ResponseEntity<List<Object>> getUserInfo(final RequestEntity<String> requestEntity, final ParameterizedTypeReference<List<Object>> typeReference) {
        return this.restTemplate.exchange(requestEntity, typeReference);
    }
}
