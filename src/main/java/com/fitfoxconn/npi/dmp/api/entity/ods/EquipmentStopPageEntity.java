package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"EquipmentStoppage\"", schema = "scada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentStopPageEntity {
  /** 流水號 */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "\"Id\"", nullable = false)
  private int id;

  /** 生產線ID */
  @Column(name = "\"LineId\"")
  private Integer lineId ;
  /** 班別 */
  @Column(name = "\"ClassId\"")
  private Integer shift;
  /** 設備編號 */
  @Column(name = "\"EquipNo\"")
  private String equipmentNo;

  /** 設備狀態碼 */
  @Column(name = "\"EqErrorCodeId\"")
  private int errorCodeId ;

  /** 開始時間 */
  @Column(name = "\"StartTime\"")
  private LocalDateTime startTime;

  /** 結束時間 */
  @Column(name = "\"EndTime\"")
  private LocalDateTime endTime ;

  /** 狀態碼描述。實際應為上一個狀態碼 */
  @Column(name = "\"EqStateRemark\"")
  private String stateRemark ;

  /** 事件發生日期 */
  @Column(name = "\"Date\"")
  private LocalDate eventDate;

  /** 休息時間 */
  @Column(name = "\"XiuxiTime\"")
  private Integer breakTime ;

  /** 設備ID */
  @Column(name = "\"EqID\"")
  private Integer equipmentId ;

  /** 持續時間 */
  @Column(name = "\"InterValTime\"")
  private Double duration ;

  /** 設備錯誤碼 */
  @Column(name = "\"EqErrorId\"")
  private Integer errorId ;

  /** 資料版本(排序用時間) */
  @Column(name = "\"DataVersion\"")
  private Long dataVersion ;

  /** 數據更新時間 */
  @Column(name = "\"DDate\"")
  private LocalDateTime createTime ;

  /** 備註 */
  @Column(name = "\"Remark\"")
  private String remark ;

  /** 所屬線別的組織碼 */
  @Column(name = "\"OrganizeCode\"")
  private String lineOrganizationCode ;

  /** 狀態名稱 */
  @Column(name = "\"StateName\"")
  private String stateName ;

  /** work數據產品編號 */
  @Column(name = "\"Work_Remark\"")
  private String workRemark ;

  /** work標識 */
  @Column(name = "\"WorkState\"")
  private String workState ;

  /** 設備異常明細 */
  @Column(name = "\"EqErrorLis\"")
  private String errorList ;

  /** 是否結束 */
  @Column(name = "\"IsEnd\"")
  private Boolean isEnd ;
}
