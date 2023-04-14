package com.fitfoxconn.npi.dmp.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpServletRequestUtil {

  /**
   * 取得Bearer token中的 sub訊息
   *
   * @param request    {@Code HttpServletRequest} Http Servlet Request
   * @param jwtDecoder {@Code JwtDecoder} jwt token decoder
   */
  public static String getUsername(HttpServletRequest request, JwtDecoder jwtDecoder) {
    String authorizationString = request.getHeader("Authorization");
    if (authorizationString != null && !authorizationString.isEmpty()
        && authorizationString.contains("Bearer")) {
      String token = request.getHeader("Authorization").substring(7);
      String requestUrl = request.getRequestURI().toString();

      // 使用 JwtDecoder 解码 JWT Token，并获取其 username 数据
      Jwt jwt = jwtDecoder.decode(token);
      return (String) jwt.getClaims().get("sub");
    }
    return "";
  }

  /**
   * @return
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return requestAttributes.getRequest();
  }
}
