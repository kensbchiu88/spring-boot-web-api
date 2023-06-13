package com.fitfoxconn.npi.dmp.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/equipment/single-dated-classed-performance-kpis api
 */
@Builder
@Data
public class GetSingleDatedClassedPerformanceKpisRs {
  @Schema(description = "日期", example = "2023-05-07")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate date;

  @Schema(description = "課別結束時間", example = "10:00:00")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
  private LocalTime time;

  @Schema(description = "KPI資料")
  private List<Kpis> kpis;

  @lombok.Data
  public static class Kpis {
    @Schema(description = "設備編號", example = "5710")
    private int equipmentId;
    @Schema(description = "設備名稱", example = "SKT-E包裝機")
    private String equipmentName;
    @Schema(description = "所屬line名稱", example = "RF020#3")
    private String lineName;
    @Schema(description = "所屬function名稱", example = "3F裝配")
    private String functionName;
    @Schema(description = "計畫量(pcs)", example = "1600")
    private Integer plannedProduction;
    @Schema(description = "產出目標(pcs)", example = "1402")
    private Integer productionTarget;
    @Schema(description = "實際產出(pcs)", example = "587")
    private Integer totalCount;
    @Schema(description = "良品數(pcs)", example = "587")
    private Integer goodCount;
    @Schema(description = "良率目標(%)", example = "80")
    private Double qualityTarget;
    @Schema(description = "良率實際(%)", example = "100")
    private BigDecimal quality;
    @Schema(description = "達成率(%)", example = "90")
    private BigDecimal achievementRate;
    @Schema(description = "總投入時間(秒)", example = "27966")
    private Double totalTime;
    @Schema(description = "宕機時間(秒)", example = "0")
    private Double unplannedStopTime;
    @Schema(description = "計畫停機時間(秒)", example = "15423")
    private Double plannedStopTime;
    @Schema(description = "負荷時間(秒)", example = "12543")
    private Double plannedProductionTime;
    @Schema(description = "稼動時間(秒)", example = "12543")
    private Double runTime;
    @Schema(description = "時間稼動率(%)", example = "39.56")
    private BigDecimal availability;
    @Schema(description = "理論週期", example = "15.0")
    private Double idealCycleTime;
    @Schema(description = "性能稼動率(%)", example = "86.02")
    private BigDecimal performance;
    @Schema(description = "OEE1", example = "85.4")
    private BigDecimal oee1;
    @Schema(description = "OEE", example = "80.75")
    private BigDecimal oee;
  }
}
