package io.cde.oauth2.client.agent;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.scribejava.core.model.*;

import io.cde.oauth2.client.builder.RequestInfoBuilder;

/**
 * Created by liaofangcai.
 * Created time 12/9/16.
 */
@Component
public class RequestAgent {

    private final Logger logger = LoggerFactory.getLogger(RequestAgent.class);

    @Autowired
    private RequestInfoBuilder builder;

    /**
     * 向服务器请求资源的url.
     */
    @Value("${cde.oauth2.client.requestUserUrl}")
    private String requestUserUrl;

    /**
     * 根据获取的code，请求token.
     * @param code this code
     * @return this token
     * @throws InterruptedException this InterruptedException
     * @throws ExecutionException this ExecutionException
     */
    public OAuth2AccessToken getAccessToken(final String code) throws InterruptedException, ExecutionException {
        final OAuth2AccessToken token;
        token = this.builder.getOAuth20Service().getAccessTokenAsync(code, new OAuthAsyncRequestCallback<OAuth2AccessToken>() {
            @Override
            public void onCompleted(final OAuth2AccessToken oAuth2AccessToken) {
                logger.info("getAccessTokenAsync OAuth2AccessToken = ", oAuth2AccessToken);
            }

            @Override
            public void onThrowable(final Throwable throwable) {
                logger.error("getAccessTokenAsync is error", throwable);
            }
        }).get();
        return token;
    }

    /**
     *
     * 根据获取的token，请求用户基本信息.
     * @param token this token
     * @return this user information
     * @throws InterruptedException this InterruptedException
     * @throws ExecutionException this ExecutionException
     * @throws IOException this IOException
     */
    public String getUserInfo(final OAuth2AccessToken token) throws InterruptedException, ExecutionException, IOException {
        final String userInfo;
        final Response response;
        final OAuthRequestAsync requestAsync = new OAuthRequestAsync(Verb.GET, requestUserUrl, this.builder.getOAuth20Service());
        this.builder.getOAuth20Service().signRequest(token, requestAsync);
        response = requestAsync.sendAsync(new OAuthAsyncRequestCallback<Response>() {
            @Override
            public void onCompleted(final Response response) {
                logger.info("sendAsync Response.body", response);
            }

            @Override
            public void onThrowable(final Throwable throwable) {
                logger.error("sendAsync is error", throwable);
            }
        }).get();
        userInfo = response.getBody();
        return userInfo;
    }
}