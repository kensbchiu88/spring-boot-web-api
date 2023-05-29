package com.fitfoxconn.npi.dmp.api.service.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Spring Data Projection (interface-based projection) for OtOpenWorkOrderDao.findByShowDateBetween
 * reference: https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions
 */
public interface OpenWorkOrder {

  /**
   * 登記為未結工單的時間
   */
  LocalDate getShowDate();

  /**
   * 取消為未結工單的時間
   */
  LocalDate getHideDate();

  /**
   * 庫存組織
   */
  String getOrganizationName();

  /**
   * 工單號
   */
  String getWorkNumber();

  /**
   * 類別
   */
  String getClassCode();

  /**
   * 狀態
   */
  String getStatus();

  /**
   * 創建人
   */
  String getCreater();

  /**
   * 裝配件料號
   */
  String getAssemblyPartNumber();

  /**
   * 裝配件名稱
   */
  String getAssemblyPartName();

  /**
   * 工單開始日期
   */
  LocalDateTime getStartDate();

  /**
   * 計劃完工數量
   */
  Integer getTargetQuantity();

  /**
   * 實際完工數量
   */
  Integer getCompletedQuantity();

  /**
   * 組件料號
   */
  String getComponentPartNumber();

  /**
   * 組件名稱
   */
  String getComponentPartName();

  /**
   * 單位
   */
  String getUnit();

  /**
   * 單位用量
   */
  Double getQuantityPerAssembly();

  /**
   * 組件良品率
   */
  Double getComponentYieldFactor();

  /**
   * 需求數量
   */
  Double getRequiredQuantity();

  /**
   * 實際發料數量
   */
  Double getQuantityIssued();

  /**
   * 課別
   */
  String getClassId();

  /**
   * 首次發料過帳日期
   */
  LocalDateTime getProgramUpdateDate();

  /**
   * 實際使用數量
   */
  BigDecimal getQuantityUsed();

  /**
   * 實際消耗數量
   */
  BigDecimal getQuantityLoss();

  /**
   * 實際消耗率
   */
  BigDecimal getLossRatio();

  /**
   * 工齡
   */
  Integer getOrderAge();
}
