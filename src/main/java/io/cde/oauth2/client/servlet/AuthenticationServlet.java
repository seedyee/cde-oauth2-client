package io.cde.oauth2.client.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.client.build.RequestBuilder;
import io.cde.oauth2.client.domain.AuthorizationCodeRequest;


/**
 * Created by liaofangcai.
 * Created time 11/27/16.
 */
@WebServlet(urlPatterns = "/authentication")
public class AuthenticationServlet extends HttpServlet {

    /**
     * 日志记录.
     */
    private final Logger logger = LoggerFactory.getLogger(AuthenticationServlet.class);

    /**
     * 获取请求code的url的参数列表.
     */
    @Autowired
    private RequestBuilder requestBuilder;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        logger.info("authentication start");
        final AuthorizationCodeRequest authorizationCodeRequest = requestBuilder.getCodeRequestUrl();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(authorizationCodeRequest.getRequestAuthorizeUrl());
        uriComponentsBuilder.queryParam("client_id", authorizationCodeRequest.getClientId());
        uriComponentsBuilder.queryParam("scope", authorizationCodeRequest.getScope());
        uriComponentsBuilder.queryParam("redirect_uri", authorizationCodeRequest.getRedirectUrl());
        uriComponentsBuilder.queryParam("state", authorizationCodeRequest.getState());
        uriComponentsBuilder.queryParam("response_type", authorizationCodeRequest.getResponseType());
        try {
            resp.sendRedirect(uriComponentsBuilder.toUriString());
        } catch (IOException e) {
            logger.error("AuthenticationServlet error", e);
        }
    }
}
