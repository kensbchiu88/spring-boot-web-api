package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.common.exception.ValidationException;
import com.fitfoxconn.npi.dmp.api.model.ChangePasswordRq;
import com.fitfoxconn.npi.dmp.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 使用者管理功能 controller
 */
@Tag(name = "UserControllerV1", description = "使用者管理功能API")
@RestController
@RequestMapping("/api/v1/user")
public class UserControllerV1 {

  @Autowired
  private JwtDecoder jwtDecoder;

  @Autowired
  private UserService userService;

  @PutMapping(value = "/password")
  @Operation(summary = "使用者修改密碼", security = @SecurityRequirement(name = "bearerAuth"))
  public void changePassword(@Parameter(required = true, name = "修改密碼資訊") @Valid @RequestBody ChangePasswordRq changePasswordRq)
      throws ValidationException {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String token = request.getHeader("Authorization").substring(7);

    // 使用 JwtDecoder 解码 JWT Token，并获取其 username 数据
    Jwt jwt = this.jwtDecoder.decode(token);
    String username = (String) jwt.getClaims().get("sub");

    this.userService.changePassword(username, changePasswordRq.getOldPassword(),
        changePasswordRq.getNewPassword(), changePasswordRq.getConfirmedNewPassword());
  }

}
