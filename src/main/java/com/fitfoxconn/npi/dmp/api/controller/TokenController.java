package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetTokenRs;
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

//  @Value("${oauth2.client.scope:}")
//  private String oauth2ClientScope;

  /*
  @GetMapping("/getToken")
  public void getAuthorizationCode() {
    WebClient client = WebClient.create();

    String loginPage = String.format("%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s", authorizeEndPoint, oauth2ClientId, oauth2ClientScope,oauth2ClientRedirectUri);

    String authorizeUrl = client.get().uri(loginPage)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    System.out.println("----authorizeUrl----" + authorizeUrl);
  }
*/

  @GetMapping("/token")
  @ResponseBody
  public GetTokenRs getTokenByCode(@RequestParam("code") String code) {
    System.out.println("----code----" + code);
    System.out.println("----oauth2ClientId----" + oauth2ClientId);
    System.out.println("----oauth2ClientSecret----" + oauth2ClientSecret);
    System.out.println("----oauth2ClientRedirectUri----" + oauth2ClientRedirectUri);
    System.out.println("----tokenEndPoint----" + tokenEndPoint);

    String authString = this.oauth2ClientId + ":" + this.oauth2ClientSecret;
//    String authString = this.oauth2ClientId + ":" + new BCryptPasswordEncoder().encode(this.oauth2ClientSecret);


    WebClient client = WebClient.create();
    String encodedClientData =
//                Base64Utils.encodeToString("dmp-api:1111".getBytes());
        Base64Utils.encodeToString(authString.getBytes());
    WebClient.ResponseSpec responseSpec = client.post()
//        .uri("http://127.0.0.1:8000/oauth2/token")
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

    /*
    @GetMapping("/login")
    public void requestToken() {
        WebClient client = WebClient.create();
        client.get()
                .uri("http://127.0.0.1:8000/oauth2/authorize?response_type=code&client_id=articles-client&scope=articles.read&redirect_uri=http://127.0.0.1:8001/gettoken")
                .retrieve().bodyToMono(String.class);
    }
    */

}
