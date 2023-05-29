package com.fitfoxconn.npi.dmp.api.entity.ods;

import com.fitfoxconn.npi.dmp.api.entity.api.RbacUserRolePk;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ot_open_work_order", schema = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtOpenWorkOrderEntity {

  @EmbeddedId
  private OtOpenWorkOrderPk id;

  /** 登記為未結工單的時間 */
  @Column(name = "\"ShowDate\"", nullable = false)
  private LocalDate showDate;

  /** 取消為未結工單的時間 */
  @Column(name = "\"HideDate\"")
  private LocalDate hideDate;

  /** 庫存組織 */
  @Column(name = "\"OrganizationName\"")
  private String organizationName;

  /** 工單號 */
  @Column(name = "\"WorkNumber\"", nullable = false)
  private String workNumber;

  /** 類別 */
  @Column(name = "\"ClassCode\"")
  private String classCode;

  /** 狀態 */
  @Column(name = "\"Status\"")
  private String status;

  /** 創建人 */
  @Column(name = "\"Creater\"")
  private String creater;

  /** 裝配件料號 */
  @Column(name = "\"AssemblyPartNumber\"", nullable = false)
  private String assemblyPartNumber;

  /** 裝配件名稱 */
  @Column(name = "\"AssemblyPartName\"")
  private String assemblyPartName;

  /** 工單開始日期 */
  @Column(name = "\"StartDate\"")
  private LocalDateTime startDate;

  /** 計劃完工數量 */
  @Column(name = "\"TargetQuantity\"")
  private Integer targetQuantity;

  /** 實際完工數量 */
  @Column(name = "\"CompletedQuantity\"")
  private Integer completedQuantity;

  /** 組件料號 */
  @Column(name = "\"ComponentPartNumber\"", nullable = false)
  private String componentPartNumber;

  /** 組件名稱 */
  @Column(name = "\"ComponentPartName\"")
  private String componentPartName;

  /** 單位 */
  @Column(name = "\"Unit\"")
  private String unit;

  /** 單位用量 */
  @Column(name = "\"QuantityPerAssembly\"")
  private Double quantityPerAssembly;

  /** 組件良品率 */
  @Column(name = "\"ComponentYieldFactor\"")
  private Double componentYieldFactor;

  /** 需求數量 */
  @Column(name = "\"RequiredQuantity\"")
  private Double requiredQuantity;

  /** 實際發料數量 */
  @Column(name = "\"QuantityIssued\"")
  private Double quantityIssued;

  /** 課別 */
  @Column(name = "\"ClassId\"")
  private String classId;

  /** 首次發料過帳日期 */
  @Column(name = "\"ProgramUpdateDate\"")
  private LocalDateTime programUpdateDate;

}
