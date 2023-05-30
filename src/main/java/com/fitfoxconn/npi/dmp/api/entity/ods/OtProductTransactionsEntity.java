package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ot_product_transactions", schema = "ebs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtProductTransactionsEntity {
  /** 交易識別碼 */
  @Id
  @Column(name = "transaction_id", nullable = false)
  private String transactionId;


  /** 產品料號 */
  @Column(name = "part_number")
  private String partNumber;


  /** 產品名稱 */
  @Column(name = "description")
  private String description;


  /** 單位 */
  @Column(name = "unit")
  private String unit;

  /** 數量 */
  @Column(name = "quantity")
  private Double quantity;


  /** 庫存組織代碼 */
  @Column(name = "subinventory_code")
  private String subinventoryCode;

  /** 查找日期 */
  @Column(name = "search_date", nullable = false)
  private LocalDateTime searchDate;

  /** 更新時間 */
  @Column(name = "update_date", nullable = false)
  private LocalDateTime updateDate;

}
