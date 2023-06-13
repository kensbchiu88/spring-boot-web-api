package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"Tj_OEE\"", schema = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogTjOeeEntity {

  /** 流水號 */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "sid", nullable = false)
  private long sid;

  /** 原Tj_OEE的ID */
  @Column(name = "\"ID\"", nullable = false)
  private int id;

  /** 班別日期 */
  @Column(name = "\"ClassDate\"")
  private LocalDate shiftDate;

  /** 組織代碼 */
  @Column(name = "\"OrganizeCode\"")
  private String organizeCode ;

  /** 線別ID */
  @Column(name = "\"lineid\"")
  private Integer lineId;

  /** "班別ID（1：白班 2：夜班）" */
  @Column(name = "\"ClassId\"")
  private Integer shift;

  /** 設備編號 */
  @Column(name = "\"EquipNo\"")
  private Integer equipmentNo;

  /** 總投入時間/秒 */
  @Column(name = "\"ZongtouruTime\"")
  private Double totalTime;

  /** 當機時間/秒 */
  @Column(name = "\"DangjiTime\"")
  private Double unplannedStopTime;

  /** 休息時間/秒 */
  @Column(name = "\"xiuxitime\"")
  private Double breakTime;

  /** 計畫停機時間/秒 */
  @Column(name = "\"JhTingjiTime\"")
  private Double plannedStopTime;

  /** 負荷時間/秒 */
  @Column(name = "\"fuheTime\"")
  private Double plannedProductionTime;

  /** 稼動時間/秒 */
  @Column(name = "\"JiadongTime\"")
  private Double runTime;

  /** 當前產量 */
  @Column(name = "\"mQty\"")
  private Integer totalCount;

  /** 理論週期 */
  @Column(name = "\"mrate\"")
  private Double idealCycleTime;

  /** 良品數 */
  @Column(name = "\"passqty\"")
  private Integer goodCount;

  /** 時間稼動率% */
  @Column(name = "\"TimeJdl\"")
  private BigDecimal availability;

  /** 良率 */
  @Column(name = "\"PassqtyLv\"")
  private BigDecimal quality;

  /** 性能稼動率% */
  @Column(name = "\"XingnengJdl\"")
  private BigDecimal performance;

  /** OEE1 */
  @Column(name = "\"OEE1\"")
  private BigDecimal oee1;

  /** OEE */
  @Column(name = "\"OEE\"")
  private BigDecimal oee;

  /** 實際週期 */
  @Column(name = "\"frate\"")
  private Double actualCycleTime;

  /** 目標產量 */
  @Column(name = "\"TargetQty\"")
  private Integer productionTarget;

  /** 設備ID */
  @Column(name = "\"EqID\"")
  private Integer equipmentId;

  /** 設備狀態 */
  @Column(name = "\"EqSate\"")
  private Integer equipmentSate;

  /** 數據更新時間 */
  @Column(name = "\"dDate\"")
  private LocalDateTime updateTime;

  /** 淨稼動時間 */
  @Column(name = "\"WorkTime\"")
  private Double netRunTime;

  /** 機故時間/秒 */
  @Column(name = "\"FaultTime\"")
  private Double faultTime;

  /** 待料時間 */
  @Column(name = "\"WaitTime\"")
  private Double waitTime;

  /** 連續停機時間/秒 */
  @Column(name = "\"ShaltTime\"")
  private Double shaltTime;

  /** 計劃產量 */
  @Column(name = "\"planProduce\"")
  private Integer plannedProduction;

  /** 達成率 */
  @Column(name = "\"yieldRate\"")
  private BigDecimal achievementRate;

  /** 不良品數 */
  @Column(name = "\"badnessQty\"")
  private Integer badCount;

  /** 異常碼 */
  @Column(name = "\"Errorcode\"")
  private String errorCode;

  /** 生產節拍 */
  @Column(name = "\"TT\"")
  private BigDecimal tt;

  /** 投入產量 */
  @Column(name = "\"InputQty\"")
  private Integer inputCount;

  /** 模具異常碼 */
  @Column(name = "\"MoldErrorCode\"")
  private String mouldErrorCode;

  /** 產品ID */
  @Column(name = "\"PRODUCTID\"")
  private String productId;

  /** 模具編號 */
  @Column(name = "\"MouldNo\"")
  private String mouldNo;

  /** 達成率目標 */
  @Column(name = "\"DCLMb\"")
  private Double achievementRateTarget;

  /** 操作者工號 */
  @Column(name = "\"OperatorNo\"")
  private String operatorNo;

  /** 工單訊息 */
  @Column(name = "\"Message\"")
  private String message;

  /** 小停頓次數 */
  @Column(name = "\"BTBA\"")
  private Integer btba;

  /** 工單號碼 */
  @Column(name = "\"WorkOrder\"")
  private String workOrder;
}
