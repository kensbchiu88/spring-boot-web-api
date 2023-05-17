package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.EqErrorTimeEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * EqErrorTime dao
 */
@Repository
public interface EqErrorTimeDao extends JpaRepository<EqErrorTimeEntity,Integer> {

  /**
   * 查詢錯誤次數與錯誤總時間
   *
   * @param equipmentId equipment id
   * @param shift 班別
   * @param startDate 班別日期的開始日期
   * @param endDate 班別日期的結束日期
   *
   * @return {@code List<TjOeeEntity>}
   */
  @Query(value = "select e.eventDate, count(*), sum(e.duration) "
      + " from EqErrorTimeEntity e "
      + " where e.equipmentId = ?1 "
      + "   and e.shift = ?2 "
      + "   and e.eventDate between ?3 and ?4 "
      + "   group by e.eventDate ")
  public List<Object[]> getSummaryAndCountByEquipmentIdAndShiftAndEventDateBetween(
      Integer equipmentId, Integer shift,
      LocalDate startDate, LocalDate endDate);

  /**
   * 查詢錯誤次數與錯誤總時間
   *
   * @param queryDate 班別日期
   * @param shift 班別
   * @param equipmentIds 設備編號list
   *
   * @return {@code List<TjOeeEntity>}
   */
  @Query(value = "select e.equipmentId, count(*), sum(e.duration) "
      + " from EqErrorTimeEntity e "
      + " where e.eventDate = ?1 "
      + "   and e.shift = ?2 "
      + "   and e.equipmentId in ?3 "
      + "   group by e.equipmentId ")
  public List<Object[]> getSummaryAndCountByShiftDateAndShiftAndEquipmentIdIn(LocalDate queryDate,
      int shift,
      List<Integer> equipmentIds);
}
