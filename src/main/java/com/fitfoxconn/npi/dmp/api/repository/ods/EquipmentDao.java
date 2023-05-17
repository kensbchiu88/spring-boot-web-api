package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.EquipmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Equipments dao
 */
@Repository
public interface EquipmentDao extends JpaRepository<EquipmentEntity, Integer> {

  /**
   * 查詢設備資訊
   *
   * @param ids 設備編號list
   *
   * @return {@code List<EquipmentEntity>}
   */
  public List<EquipmentEntity> findByIdIn(List<Integer> ids);

  /**
   * 查詢設備資訊
   *
   * @param lineCodes 線別組織碼list
   *
   * @return {@code List<EquipmentEntity>}
   */
  public List<EquipmentEntity> findByIsShowTrueAndLineOrganizeCodeInOrderByLineOrganizeCode(List<String> lineCodes);
}
