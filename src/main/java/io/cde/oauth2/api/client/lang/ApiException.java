package io.cde.oauth2.api.client.lang;

import org.springframework.http.ResponseEntity;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求服务器异常-code、token
 */
public class ApiException extends RuntimeException {

    private final ResponseEntity<?> responseEntity;

    public ApiException(ResponseEntity<?> responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public String getMessage() {
        return "API response entity: [" + responseEntity + "]";
    }
}
