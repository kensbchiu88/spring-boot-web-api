package com.fitfoxconn.npi.dmp.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/equipment/states api
 */
@Builder
@Data
public class GetStatesRs {
  @Schema(description = "日期", example = "2023-05-07")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate date;
  @Schema(description = "設備狀態")
  private List<EquipmentStateList> EquipmentStates;

  @Data
  public static class EquipmentStateList {
    @Schema(description = "設備編號", example = "5710")
    private int equipmentId;
    @Schema(description = "設備名稱", example = "SKT-E包裝機")
    private String equipmentName;
    @Schema(description = "所屬line名稱", example = "RF020#3")
    private String lineName;
    @Schema(description = "所屬function名稱", example = "3F裝配")
    private String functionName;
    @Schema(description = "設備狀態")
    private List<State> states;
  }

  @Data
  public static class State {
    @Schema(description = "狀態碼", example = "7")
    private Integer stateCode;
    @Schema(description = "狀態名稱", example = "待料")
    private String stateName;
    @Schema(description = "開始時間", example = "2023-05-12 17:59:27.976")
    private LocalDateTime startTime;
    @Schema(description = "結束時間", example = "2023-05-12 18:24:33.946")
    private LocalDateTime endTime;
    @Schema(description = "持續時間(秒)", example = "1505.971")
    private Double duration;
  }

}
