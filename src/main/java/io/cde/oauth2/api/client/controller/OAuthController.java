package io.cde.oauth2.api.client.controller;

import java.net.URISyntaxException;
import java.util.List;

import io.cde.oauth2.api.client.lang.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.api.client.service.AccessTokenService;
import io.cde.oauth2.api.client.service.UserService;

/**
 * Created by liaofangcai on 11/21/16.
 * 请求code和token的api接口.
 */
@RestController
public class OAuthController {

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
     * 组装获取code的url.
     */
    @Autowired
    private UriComponentsBuilder uriComponentsBuilder;

    private String accessToken = null;

    private List list = null;
    /**
     * 访问项目首页.
     * @return 请求code的请求url.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return uriComponentsBuilder.toUriString();
    }
    /**
     * @param code code用于换取token的必填参数.
     * @return 返回请求的用户数据.
     */
    @RequestMapping("/callback")
    public List callback(@RequestParam("code") final String code) {
        try {
            accessToken = accessTokenService.getAccessTokenByCode(code);
            list = userService.getUserInforByAccessToken(accessToken);
            return list;
        } catch (ApiException ex) {
            ex.printStackTrace();
            return null;
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            return null;
        }  catch (RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
