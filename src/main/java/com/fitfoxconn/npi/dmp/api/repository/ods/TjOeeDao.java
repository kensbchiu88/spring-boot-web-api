package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.TjOeeEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tj_OEE dao
 */
@Repository
public interface TjOeeDao extends JpaRepository<TjOeeEntity, Integer> {

  /**
   * 查詢Tj_OEE資料
   *
   * @param equipmentId equipment id
   * @param shift 班別
   * @param startDate 班別日期的開始日期
   * @param endDate 班別日期的結束日期
   *
   * @return {@code List<TjOeeEntity>}
   */
  public List<TjOeeEntity> findByEquipmentIdAndShiftAndShiftDateBetween(int equipmentId, int shift,
      LocalDate startDate, LocalDate endDate);

  /**
   * 查詢Tj_OEE資料
   *
   * @param queryDate 班別日期
   * @param shift 班別
   * @param equipmentIds 設備編號list
   *
   * @return {@code List<TjOeeEntity>}
   */
  public List<TjOeeEntity> findByShiftDateAndShiftAndEquipmentIdIn(LocalDate queryDate, int shift,
      List<Integer> equipmentIds);

}
