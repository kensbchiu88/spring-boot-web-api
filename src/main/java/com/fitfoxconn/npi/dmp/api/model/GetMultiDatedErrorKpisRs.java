package com.fitfoxconn.npi.dmp.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/equipment/multi-dated-error-kpis api
 */
@Builder
@Data
public class GetMultiDatedErrorKpisRs {
  @Schema(description = "設備編號", example = "5710")
  private int equipmentId;
  @Schema(description = "設備名稱", example = "SKT-E包裝機")
  private String equipmentName;
  @Schema(description = "所屬line名稱", example = "RF020#3")
  private String lineName;
  @Schema(description = "所屬function名稱", example = "3F裝配")
  private String functionName;
  @Schema(description = "KPI資料")
  private List<Kpis> kpis;
  @lombok.Data
  public static class Kpis {
    @Schema(description = "日期", example = "2023-05-07")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @Schema(description = "故障次數", example = "16")
    private Long errorCount;
    @Schema(description = "故障總時間(秒)", example = "1402")
    private Double errorTotalTime;
    @Schema(description = "總投入時間(秒)", example = "27966")
    private Double totalTime;
    @Schema(description = "平均修復時間(秒)", example = "587")
    private Double mttr;
    @Schema(description = "平均故障間隔(秒)", example = "80")
    private Double mtbf;
  }
}
