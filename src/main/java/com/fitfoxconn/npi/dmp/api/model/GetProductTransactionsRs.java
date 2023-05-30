package com.fitfoxconn.npi.dmp.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Response of /api/v1/ebs/product-transactions api
 */
@Data
@Builder
public class GetProductTransactionsRs {

  @Schema(description = "查找日期", example = "2023-05-22")
  private LocalDate searchDate;

  @Schema(description = "庫存明細")
  private List<ProductTransaction> data;

  @Data
  public static class ProductTransaction {

    @Schema(description = "交易識別碼", example = "499469194")
    private String transactionId;

    @Schema(description = "產品料號", example = "QT4013Y-D0332-7H")
    private String partNumber;

    @Schema(description = "產品名稱", example = "BTB 0.4P 3.2H 26POS Plug Connector")
    private String description;

    @Schema(description = "單位", example = "PC")
    private String unit;

    @Schema(description = "數量", example = "20800")
    private Double quantity;

    @Schema(description = "庫存組織代碼", example = "MFT61NFT")
    private String subinventoryCode;

    @Schema(description = "更新時間", example = "2023-05-25 09:17:57")
    private LocalDateTime updateDate;
  }
}
