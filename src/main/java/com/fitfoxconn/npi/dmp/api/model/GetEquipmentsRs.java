package com.fitfoxconn.npi.dmp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/organization/line/equipments api
 */
@Data
@Builder
public class GetEquipmentsRs {
  @Schema(description = "線別組織碼", example = "0602010502")
  private String lineCode;
  @Schema(description = "設備代碼", example = "[2888]")
  private List<Integer> equipmentIds;
}
