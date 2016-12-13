package io.cde.oauth2.client.builder;

import java.util.Random;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import io.cde.oauth2.client.repository.RedisRepository;
import io.cde.oauth2.client.unit.InitHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by liaofangcai.
 * Created time 12/12/16.
 */
@Component
public class RequestInfoBuilder {

    /**
     * 表示授权类型.
     */
    private final String responseType = "code";

    /**
     * 项目注册id.
     */
    @Value("${cde.oauth2.client.clientId}")
    private String clientId;

    /**
     * 项目注册的Client_Secret.
     */
    @Value("${cde.oauth2.client.clientSecret}")
    private String clientSecret;

    /**
     * 请求的授权.
     */
    @Value("${cde.oauth2.client.scope}")
    private String scope;

    /**
     * 请求授权登录获取code数据之后的回调url.
     */
    @Value("${cde.oauth2.client.redirectUrl}")
    private String redirectUrl;

    /**
     * 请求的授权.
     */
    @Value("${cde.oauth2.client.timeToLiveSeconds}")
    private long timeToLiveSeconds;

    @Autowired
    RedisRepository redisRepository;

    /**
     * 获取 OAuth20Service 实例.
     * @return this OAuth20Service
     */
    public OAuth20Service getOAuth20Service() {
        final String state = "state" + new Random().nextInt(8888888);
        this.redisRepository.set(state, state, timeToLiveSeconds);
        final OAuth20Service service = new ServiceBuilder()
            .responseType(this.responseType)
            .httpClient(InitHttpClient.getNingHttpClient())
            .scope(this.scope)
            .apiKey(this.clientId)
            .apiSecret(this.clientSecret)
            .state(state)
            .callback(this.redirectUrl)
            .build(GitHubApi.instance());
        return service;
    }
}
