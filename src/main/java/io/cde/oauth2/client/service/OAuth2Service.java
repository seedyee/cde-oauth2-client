package io.cde.oauth2.client.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.scribejava.core.model.OAuth2AccessToken;

import io.cde.oauth2.client.agent.RequestAgent;
import io.cde.oauth2.client.builder.RequestInfoBuilder;
import io.cde.oauth2.client.repository.MongodbRepository;
import io.cde.oauth2.client.repository.RedisRepository;

/**
 * Created by liaofangcai.
 * Created time 12/12/16.
 */
@Service
public class OAuth2Service {

    /**
     * 日志记录.
     */
    private Logger logger = LoggerFactory.getLogger(OAuth2Service.class);

    /**
     * agent调用第三方API.
     */
    @Autowired
    private RequestAgent agent;

    /**
     * 构建OAuth20Service.
     */
    @Autowired
    private RequestInfoBuilder builder;

    /**
     * 操作mongodb.
     */
    @Autowired
    private MongodbRepository mongodbRepository;

    /**
     * 操作redis.
     */
    @Autowired
    private RedisRepository redisRepository;

    /**
     * 获取请求code的登录收取url.
     * @return this url
     */
    public String getRequestCodeUrl() {
        final String url;
        url = this.builder.getOAuth20Service().getAuthorizationUrl();
        return url;
    }

    /**
     *
     * 根据code通过agent代理请求第三方API，获取token信息.
     * @param code this.code
     * @return this token
     * @throws InterruptedException this InterruptedException
     * @throws ExecutionException this ExecutionException
     */
    public OAuth2AccessToken getAccessTokenByCode(final String code) throws InterruptedException, ExecutionException {
        final OAuth2AccessToken token;
        token = this.agent.getAccessToken(code);
        return token;
    }

    /**
     *
     * 根据token通过agent代理请求第三方API，获取用户基本信息.
     * @param token this token
     * @return this userInfo
     * @throws InterruptedException this InterruptedException
     * @throws ExecutionException this ExecutionException
     * @throws IOException this IOException
     */
    public String getUserWithToken(final OAuth2AccessToken token) throws InterruptedException, ExecutionException, IOException {
        final String userInfo;
        userInfo = agent.getUserInfo(token);
        return userInfo;
    }

    /**
     * 检查github用户是否在本系统存在基本信息存档，如果没有则存储.
     * @param userInfo this userInfo
     * @param jsonObject this jsonObject
     */
    public boolean handleUserInfo(final String userInfo, final JSONObject jsonObject) {
        final boolean isuseInfoExisted;
        isuseInfoExisted = this.mongodbRepository.checkAccount(jsonObject.get("login").toString(), jsonObject.get("email").toString());
        if (!isuseInfoExisted) {
            mongodbRepository.addGitHubAccount(userInfo);
        }
        return isuseInfoExisted;
    }

    /**
     * 检查redi缓存中是否存在state,用于检验该请求是否合法.
     * @param state this state
     * @return this result
     */
    public boolean checkState(final String state) {
        final String redisState;
        final boolean result = true;
        redisState = redisRepository.get(state);
        if (redisState == null) {
            logger.error("state is null in redis");
            return false;
        }
        return result;
    }
}
