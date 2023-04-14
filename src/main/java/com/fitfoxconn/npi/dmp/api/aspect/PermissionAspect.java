package com.fitfoxconn.npi.dmp.api.aspect;

import com.fitfoxconn.npi.dmp.api.common.AccessDeniedException;
import com.fitfoxconn.npi.dmp.api.service.RbacAuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@Order(1)
public class PermissionAspect {

  @Autowired
  private JwtDecoder jwtDecoder;

  @Autowired
  private RbacAuthorizationService rbacAuthorizationService;

  /**
   * controller 切入點
   */
  @Pointcut("execution(* com.fitfoxconn.npi.dmp.api.controller..*.*(..)) "
      + "&& !execution(* com.fitfoxconn.npi.dmp.api.controller.LoginController.*(..)) "
      + "&& !execution(* com.fitfoxconn.npi.dmp.api.controller.TokenController.*(..))")
  protected void pointcut() {

  }

  @Before("pointcut()")
  public void before(JoinPoint joinPoint) throws Exception {
    log.info("########## Permission Check start");
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String token = request.getHeader("Authorization").substring(7);
    String requestUrl = request.getRequestURI().toString();

    // 使用 JwtDecoder 解码 JWT Token，并获取其 username 数据
    Jwt jwt = jwtDecoder.decode(token);
    String username = (String) jwt.getClaims().get("sub");

    if (!rbacAuthorizationService.hasPermission(username, requestUrl)) {
      throw new AccessDeniedException("Access Denied");
    }
  }

    /*
    @Around("pointcut()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        log.info("########## Permission Check start");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").substring(7);
        String requestUrl = request.getRequestURI().toString();

        // 使用 JwtDecoder 解码 JWT Token，并获取其 username 数据
        Jwt jwt = jwtDecoder.decode(token);
        String username = (String) jwt.getClaims().get("sub");

        if(rbacAuthorizationService.hasPermission(username, requestUrl)) {
            jp.proceed();
        } else {
            throw new Exception("Access Denied");
        }
    }
    */


}
