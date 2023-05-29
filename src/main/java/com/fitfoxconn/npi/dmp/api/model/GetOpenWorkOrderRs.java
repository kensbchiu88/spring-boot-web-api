package com.fitfoxconn.npi.dmp.api.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/ebs/open-work-order api
 */
@Data
@Builder
@JsonPropertyOrder({"showDate", "data"})
public class GetOpenWorkOrderRs {
  @Schema(description = "登記為未結工單的時間", example = "2023-05-22")
  private LocalDate showDate;
  @Schema(description = "未結工單資訊")
  private List<OpenWorkOrder> data;

  @Data
  public static class OpenWorkOrder {
    @Schema(description = "取消為未結工單的時間", example = "2023-05-22")
    private LocalDate hideDate;
    @Schema(description = "庫存組織", example = "FIT-TW_383_ABS2")
    private String organizationName;
    @Schema(description = "工單號", example = "213833993732")
    private String workNumber;
    @Schema(description = "類別", example = "量產")
    private String classCode;
    @Schema(description = "狀態", example = "已關閉")
    private String status;
    @Schema(description = "創建人", example = "江姿瑩")
    private String creater;
    @Schema(description = "裝配件料號", example = "KMC1001-F020-7H")
    private String assemblyPartNumber;
    @Schema(description = "裝配件名稱", example = "2.0*2.0*0.9 RF CONN.")
    private String assemblyPartName;
    @Schema(description = "工單開始日期", example = "2023-05-17 08:00:00")
    private LocalDateTime startDate;
    @Schema(description = "計劃完工數量", example = "40000")
    private Integer targetQuantity;
    @Schema(description = "實際完工數量", example = "40000")
    private Integer completedQuantity;
    @Schema(description = "組件料號", example = "030-1001-1420")
    private String componentPartNumber;
    @Schema(description = "組件名稱", example = "CONTACT I/M AFTER PLATING")
    private String componentPartName;
    @Schema(description = "單位", example = "PC")
    private String unit;
    @Schema(description = "單位用量", example = "1")
    private Double quantityPerAssembly;
    @Schema(description = "組件良品率", example = "1")
    private Double componentYieldFactor;
    @Schema(description = "需求數量", example = "40000")
    private Double requiredQuantity;
    @Schema(description = "實際發料數量", example = "41500")
    private Double quantityIssued;
    @Schema(description = "課別", example = "MMJT4S25")
    private String classId;
    @Schema(description = "首次發料過帳日期", example = "2023-05-22 09:45:09")
    private LocalDateTime programUpdateDate;
    @Schema(description = "實際使用數量", example = "40000.0000")
    private BigDecimal QuantityUsed;
    @Schema(description = "實際消耗數量", example = "1500.0000")
    private BigDecimal QuantityLoss;
    @Schema(description = "實際消耗率", example = "3.61")
    private BigDecimal LossRatio;
    @Schema(description = "工齡", example = "12.0")
    private Integer OrderAge;
  }

}
