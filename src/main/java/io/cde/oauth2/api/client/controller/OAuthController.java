package io.cde.oauth2.api.client.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import io.cde.oauth2.api.client.service.AccessTokenService;
import io.cde.oauth2.api.client.service.UserService;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求code和token的api接口.
 */
@RestController
public class OAuthController {

    /**
     * 日志记录.
     */
    private final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    /**
     * 获取token的service.
     */
    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 根据token获取用户数据的service.
     */
    @Autowired
    private UserService userService;

    /**
     * 获取code之后的回调，根据token获取用户信息.
     * @param code this code
     * @param state this state
     * @return 用户信息
     */
    @RequestMapping("/callback")
    private List callback(@RequestParam("code") final String code, @RequestParam("state") final long state) {
        final String accessToken;
        List<Object> list = new ArrayList<Object>();
        try {
            accessToken = this.accessTokenService.getAccessTokenByCode(code);
            if (accessToken == null) {
                logger.error(" The parameters of the request are inconsistent about state");
                return list;
            }
            list = this.userService.getUserByAccessToken(accessToken);
        } catch (URISyntaxException | RestClientException e) {
            logger.error("Request error about token", e);
        }
        return list;
    }
}
