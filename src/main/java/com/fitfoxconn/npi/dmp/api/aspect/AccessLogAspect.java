package com.fitfoxconn.npi.dmp.api.aspect;

import com.fitfoxconn.npi.dmp.api.common.exception.AccessDeniedException;
import com.fitfoxconn.npi.dmp.api.repository.api.AccessLogDao;
import com.fitfoxconn.npi.dmp.api.entity.api.AccessLogEntity;
import com.fitfoxconn.npi.dmp.api.util.HttpServletRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(0)
public class AccessLogAspect {

  @Autowired
  private JwtDecoder jwtDecoder;

  @Autowired
  private AccessLogDao accessLogDao;

  private ThreadLocal<AccessLogEntity> threadLocal = new ThreadLocal<>();


  /**
   * controller 切入點
   */
  @Pointcut("execution(* com.fitfoxconn.npi.dmp.api.controller..*.*(..))")
  protected void pointcut() {

  }


  @Before("pointcut()")
  public void before() {
    HttpServletRequest request = HttpServletRequestUtil.getRequest();
    String requestUrl = request.getRequestURI().toString();
    //String ip = request.getRemoteAddr();
    String ip = request.getHeader("X-Forwarded-For");
    String jwtToken = request.getHeader("Authorization");
    String username = HttpServletRequestUtil.getUsername(request, jwtDecoder);

    log.info("##########url:{}, ip:{} ", requestUrl, ip);
    log.info("##########token:{} ", jwtToken);

    AccessLogEntity logEntity = AccessLogEntity.builder()
        .requestUrl(requestUrl)
        .username(username)
        .clientIp(ip)
        .requestTime(LocalDateTime.now()).build();

    this.threadLocal.set(logEntity);
  }

/*
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint jp) throws Throwable {

    HttpServletRequest request = HttpServletRequestUtil.getRequest();
    String requestUrl = request.getRequestURI().toString();
    String ip = request.getRemoteAddr();
    String username = HttpServletRequestUtil.getUsername(request, jwtDecoder);

    AccessLogEntity logEntity = AccessLogEntity.builder()
        .requestUrl(requestUrl)
        .username(username)
        .clientIp(ip)
        .responseStatusCode("200")
        .requestTime(LocalDateTime.now()).build();

    Object result;
    try {
      result = jp.proceed();
    } catch (AccessDeniedException e1) {
      log.info("##########403");
      logEntity.setResponseStatusCode("403");
      throw e1;
    } catch (Exception e2) {
      log.info("##########500");
      logEntity.setResponseStatusCode("500");
      throw e2;
    } finally {
      Duration elapsedTime = Duration.between(logEntity.getRequestTime(), LocalDateTime.now());
      logEntity.setElapsedTime(elapsedTime.toMillis());
      this.accessLogDao.save(logEntity);
    }
    return result;
  }
*/

  @AfterReturning("pointcut()")
  public void afterReturning() {
    log.info("##########Log Aspect AfterReturning");
    AccessLogEntity logEntity = this.threadLocal.get();
    logEntity.setResponseStatusCode("200");
    Duration elapsedTime = Duration.between(logEntity.getRequestTime(), LocalDateTime.now());
    logEntity.setElapsedTime(elapsedTime.toMillis());
    this.accessLogDao.save(logEntity);
  }

  @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
  public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
    log.info("##########Log Aspect AfterThrowing");
    AccessLogEntity logEntity = this.threadLocal.get();
    if (ex instanceof AccessDeniedException) {
      log.info("##########Log Aspect AccessDeniedException");
      logEntity.setResponseStatusCode("403");
    } else {
      log.info("##########Log Aspect OtherException");
      logEntity.setResponseStatusCode("500");
    }
    Duration elapsedTime = Duration.between(logEntity.getRequestTime(), LocalDateTime.now());
    logEntity.setElapsedTime(elapsedTime.toMillis());
    this.accessLogDao.save(logEntity);
  }

}
