package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"pz_Line\"", schema = "scada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PzLineEntity {

  /** 流水號 */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "\"Id\"", nullable = false)
  private int id;

  /** 線別名稱 */
  @Column(name = "\"LineName\"")
  private String lineName ;

  /** 產品組織碼(上層組織碼) */
  @Column(name = "\"OrganizeCode\"")
  private String productOrganizationCode;

  /** 線別組織碼 */
  @Column(name = "\"LineCode\"")
  private String lineOrganizationCode ;

  /** 投入設備ID */
  @Column(name = "\"InputEqId\"")
  private Integer inputEquipmentId ;

  /** 產出設備ID */
  @Column(name = "\"OutputEqId\"")
  private Integer outputEquipmentId ;

  /** 標桿設備ID */
  @Column(name = "\"Eqid\"")
  private Integer keyEquipmentId ;

  /** 良率目標 */
  @Column(name = "\"LLMb\"")
  private Double qualityTarget ;
}
