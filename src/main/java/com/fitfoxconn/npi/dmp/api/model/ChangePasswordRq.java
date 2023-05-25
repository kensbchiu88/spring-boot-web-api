package com.fitfoxconn.npi.dmp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * Request of /api/v1/user/password api
 */
@Data
public class ChangePasswordRq {
  @NotBlank(message="舊密碼不能為空")
  @Schema(description = "舊密碼", required = true, example = "12345678")
  private String oldPassword;
  @Schema(description = "新密碼", required = true, example = "12345678")
  @NotBlank(message="新密碼不能為空")
  private String newPassword;
  @NotBlank(message="確認新密碼不能為空")
  @Schema(description = "確認新密碼", required = true, example = "12345678")
  private String confirmedNewPassword;

}
