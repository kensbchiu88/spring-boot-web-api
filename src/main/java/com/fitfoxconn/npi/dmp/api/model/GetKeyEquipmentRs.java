package com.fitfoxconn.npi.dmp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/organization/line/key-equipment api
 */
@Data
@Builder
public class GetKeyEquipmentRs {
  @Schema(description = "線別組織碼", example = "0602010502")
  private String lineCode;
  @Schema(description = "設備代碼", example = "2888")
  private Integer equipmentId;
}
