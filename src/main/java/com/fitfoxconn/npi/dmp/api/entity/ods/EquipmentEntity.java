package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"Equipments\"", schema = "scada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentEntity{

  /** 流水號 */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "\"Id\"", nullable = false)
  private int id;

  /** 是否有效 */
  @Column(name = "\"IsShow\"")
  private Boolean isShow;

  /** 設備組織碼 */
  @Column(name = "\"EquipNo\"")
  private String equipmentOrganizationCode;

  /** 設備名稱 */
  @Column(name = "\"Equipname\"")
  private String equipmentName;

  /** 所屬line的組織碼 */
  @Column(name = "\"OrganizeCode\"")
  private String lineOrganizeCode;

}
