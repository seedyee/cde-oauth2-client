package io.cde.oauth2.client.controller;

import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.cde.oauth2.client.service.AccessTokenService;
import io.cde.oauth2.client.service.UserService;
import io.cde.oauth2.client.tools.ApiException;

/**
 * Created by liaofangcai on 11/21/16.
 */
@RestController
public class OauthController {

  @Autowired
  private AccessTokenService accessTokenService;

  @Autowired
  private UserService userService;

  @Autowired
  private UriComponentsBuilder uriComponentsBuilder;

  private static final Random RANDOM = new SecureRandom();

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    final long state = RANDOM.nextLong();
    uriComponentsBuilder.replaceQueryParam("state", state);

    return uriComponentsBuilder.toUriString();
  }

  @RequestMapping("/authorized")
  public List getUserEmails(@RequestParam("code") final String code, @RequestParam("state") final long state) {
    try {
      String accessToken = accessTokenService.getAccessToken(code);
      List list = userService.getUserEmails(accessToken);
      return list;
    }catch (ApiException ex) {
      ex.printStackTrace();
      return null;
    }catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
