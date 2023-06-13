package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.LogTjOeeEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogTjOeeDao extends JpaRepository<LogTjOeeEntity, Long> {

  /**
   * 查詢Log.Tj_OEE資料
   *
   * @param queryDate 班別日期
   * @param time 課別結束時間
   * @param equipmentIds 設備編號list
   *
   * @return {@code List<LogTjOeeEntity>}
   */
  @Query(value = "with temp_oee as ( "
      + "   select rank() over( partition by \"EqID\" order by \"dDate\" desc), * "
      + "     from log.\"Scada_Tj_OEE\" e "
      + "    where \"ClassDate\" = ?1 "
      + "      and \"dDate\" < ?2 "
      + "      and \"EqID\" in ?3 "
      + "      ) "
      + "select * from temp_oee where rank = 1", nativeQuery = true)
  List<LogTjOeeEntity> getLatestByShiftDateAndTimeAndEquipmentIdIn(LocalDate queryDate, LocalDateTime time, List<Integer> equipmentIds);
}
