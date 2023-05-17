package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.EquipmentStopPageEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * EquipmentStoppage dao
 */
@Repository
public interface EquipmentStopPageDao extends JpaRepository<EquipmentStopPageEntity, Integer> {

  /**
   * 查詢錯誤次數與錯誤總時間
   *
   * @param equipmentId equipment id
   * @param shift       班別
   * @param startDate   班別日期的開始日期
   * @param endDate     班別日期的結束日期
   * @return 日期，錯誤次數，錯誤總時間
   */
  @Query(value = "select e.eventDate, count(*), sum(e.duration) "
      + " from EquipmentStopPageEntity e "
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
   * @param queryDate    班別日期
   * @param shift        班別
   * @param equipmentIds 設備編號list
   * @return {@code List<[設備代碼，錯誤次數，錯誤總時間]]>}
   */
  @Query(value = "select e.equipmentId, count(*), sum(e.duration) "
      + " from EquipmentStopPageEntity e "
      + " where e.eventDate = ?1 "
      + "   and e.shift = ?2 "
      + "   and e.equipmentId in ?3 "
      + "   group by e.equipmentId ")
  public List<Object[]> getSummaryAndCountByShiftDateAndShiftAndEquipmentIdIn(LocalDate queryDate,
      int shift,
      List<Integer> equipmentIds);


  /**
   * 查詢最後一筆資料
   *
   * @param queryDate    班別日期
   * @param shift        班別
   * @param equipmentIds 設備編號list
   * @return {@code List<EquipmentStopPage>}
   */
  @Query(value = "with temp_stoppage as ("
      + "    SELECT rank() over(partition by \"EqID\" order by \"DDate\" desc), e.* "
      + "      FROM scada.\"EquipmentStoppage\" e "
      + "      where \"Date\" = ?1 and \"ClassId\" = ?2 and \"EqID\" in ?3"
      + ")"
      + "    SELECT * "
      + "    FROM temp_stoppage where rank = 1", nativeQuery = true)
  public List<EquipmentStopPageEntity> getCurrentByShiftDateAndShiftAndEquipmentIdIn(
      LocalDate queryDate,
      int shift,
      List<Integer> equipmentIds);


  /**
   * 查詢設備狀態資料
   *
   * @param queryDate    班別日期
   * @param shift        班別
   * @param equipmentIds 設備編號list
   * @return {@code List<EquipmentStopPage>}
   */
  @Query(value = "select e "
      + " from EquipmentStopPageEntity e "
      + " where e.eventDate = ?1 "
      + "   and e.shift = ?2 "
      + "   and e.equipmentId in ?3 "
      + "   order by e.equipmentId, e.startTime ")
  public List<EquipmentStopPageEntity> getByCreateTimeAndShiftAndEquipmentIdIn(
      LocalDate queryDate,
      int shift,
      List<Integer> equipmentIds);
}
