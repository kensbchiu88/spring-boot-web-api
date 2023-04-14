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
    public String redirectToAuthorizationServer(){

        String loginPage = String.format("%s?response_type=code&client_id=%s&scope=%s&redirect_uri=%s", authorizeEndPoint, oauth2ClientId, oauth2ClientScope,oauth2ClientRedirectUri);

        System.out.println("----login uri----" + loginPage);

//        String loginPage = "http://127.0.0.1:8000/oauth2/authorize?response_type=code&client_id=dmp-api&scope=openid&redirect_uri=http://127.0.0.1:8001/token";
//        String loginPage = "http://172.20.10.2:8000/oauth2/authorize?response_type=code&client_id=articles-client&scope=articles.read&redirect_uri=http://127.0.0.1:8001/token";
        return "redirect:"+loginPage;
    }
}
