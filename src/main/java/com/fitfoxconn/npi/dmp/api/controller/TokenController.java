package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetTokenRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class TokenController {

  @Value("${oauth2.client.id:}")
  private String oauth2ClientId;

  @Value("${oauth2.client.secret:}")
  private String oauth2ClientSecret;

  @Value("${oauth2.client.redirect-uri:}")
  private String oauth2ClientRedirectUri;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:}")
  private String issuerUri;

  @Value("${oauth2.client.token-endpoint:}")
  private String tokenEndPoint;

  @Value("${oauth2.client.authorize-endpoint:}")
  private String authorizeEndPoint;

  @Value("${oauth2.client.scope:}")
  private String oauth2ClientScope;

  @GetMapping("/token")
  @ResponseBody
  @Operation(summary = "取得JWT Token")
  @Parameter(name = "code", description = "OAuth2.0 Authorization Code")
  public GetTokenRs getTokenByCode(@RequestParam("code") String code) {
    String authString = this.oauth2ClientId + ":" + this.oauth2ClientSecret;

    WebClient client = WebClient.create();
    String encodedClientData =
        Base64Utils.encodeToString(authString.getBytes());
    WebClient.ResponseSpec responseSpec = client.post()
        .uri(this.tokenEndPoint)
        .header("Authorization", "Basic " + encodedClientData)
        .body(BodyInserters.fromFormData("grant_type", "authorization_code")
            .with("code", code)
            .with("redirect_uri", this.oauth2ClientRedirectUri))
        .retrieve();
    GetTokenRs responseBody = responseSpec.bodyToMono(GetTokenRs.class).block();
    System.out.println("----body----" + responseBody);
    return responseBody;
  }

}
