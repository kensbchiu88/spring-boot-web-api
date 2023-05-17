package com.fitfoxconn.npi.dmp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 共用的Response of get organization api (function, product, line)
 */
@Builder
@Data
public class GetOrganizationCodeNameRs {
  @Schema(description = "組織編碼", example = "060202")
  private String code;
  @Schema(description = "組織名稱", example = "1F成型")
  private String name;
  @Schema(description = "組織類型", example = "Function")
  private String type;
}
