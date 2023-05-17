package com.fitfoxconn.npi.dmp.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @Value("${oauth2.client.id:}")
  private String oauth2ClientId;

  @Value("${oauth2.client.redirect-uri:}")
  private String oauth2ClientRedirectUri;

  @Value("${oauth2.client.scope:}")
  private String oauth2ClientScope;

  @Value("${oauth2.client.authorize-endpoint:}")
  private String authorizeEndPoint;

  @GetMapping("/login")
  public String redirectToAuthorizationServer() {
    String loginPage = String.format("%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s",
        authorizeEndPoint, oauth2ClientId, oauth2ClientScope, oauth2ClientRedirectUri);
    System.out.println("----login uri----" + loginPage);
    return "redirect:" + loginPage;
  }
}
