package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.entity.ods.TjOeeEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.EqErrorTimeDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.TjOeeDao;
import com.fitfoxconn.npi.dmp.api.service.ErrorKpisService.GetMultiDatedErrorKpisOutput.Kpis;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService.EquipmentInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 取得設備錯誤相關KPI的service
 */
@Service
public class ErrorKpisService {

  @Autowired
  private EqErrorTimeDao eqErrorTimeDao;
  @Autowired
  private TjOeeDao tjOeeDao;
  @Autowired
  private OrganizationUtilService organizationUtilService;

  /**
   * 抓取單一設備，多天的錯誤相關KPI
   */
  public GetMultiDatedErrorKpisOutput getMultiDatedErrorKpis(int equipmentId,
      LocalDate startDate, LocalDate endDate, ShiftEnum shiftEnum) {

    //取得EqErrorTime資訊
    List<Object[]> stopInfoList = this.eqErrorTimeDao.getSummaryAndCountByEquipmentIdAndShiftAndEventDateBetween(
        equipmentId, shiftEnum.getCode(), startDate, endDate);

    //取得總投入時間
    List<TjOeeEntity> tjOeeList = this.tjOeeDao.findByEquipmentIdAndShiftAndShiftDateBetween(
        equipmentId, shiftEnum.getCode(), startDate, endDate);
    Map<String, TjOeeEntity> tjOeeMap = tjOeeList.stream()
        .collect(Collectors.toMap(e -> e.getShiftDate().toString(), Function.identity()));

    //取得設備資訊，如設備名稱，functionName
    EquipmentInfo equipmentInfo = this.organizationUtilService.getFunctionNameMapByEquipmentId(
        equipmentId);

    //組成output
    List<GetMultiDatedErrorKpisOutput.Kpis> kpis = new ArrayList<>();
    stopInfoList.forEach(i -> {
      LocalDate eventDate = (LocalDate) i[0];
      long errorCount = (long) i[1];
      double errorTotalTime = (double) i[2];
      double totalTime = tjOeeMap.get(eventDate.toString()).getTotalTime();
      double mttr = 0;
      double mtbf = 0;
      if (errorCount > 0) {
        mttr = errorTotalTime / errorCount;
        mtbf = (totalTime - errorTotalTime) / errorCount;
      }
      GetMultiDatedErrorKpisOutput.Kpis kpi = Kpis.builder()
          .date(eventDate)
          .errorCount(errorCount)
          .errorTotalTime(errorTotalTime)
          .totalTime(totalTime)
          .mttr(mttr)
          .mtbf(mtbf)
          .build();
      kpis.add(kpi);
    });

    GetMultiDatedErrorKpisOutput result = GetMultiDatedErrorKpisOutput.builder()
        .equipmentId(equipmentInfo.getEquipmentID())
        .equipmentName(equipmentInfo.getEquipmentName())
        .lineName(equipmentInfo.getLineName())
        .functionName(equipmentInfo.getFunctionName())
        .kpis(kpis).build();

    return result;
  }

  /**
   * 抓取多個設備，某天的錯誤相關KPI
   */
  public GetSingleDatedErrorKpisOutput getSingleDatedErrorKpis(List<Integer> equipmentIds,
      LocalDate queryDate,
      ShiftEnum shiftEnum) {

    //取得EqErrorTime資訊
    List<Object[]> stopInfoList = this.eqErrorTimeDao.getSummaryAndCountByShiftDateAndShiftAndEquipmentIdIn(
        queryDate, shiftEnum.getCode(), equipmentIds);

    //取得設備資訊，如設備名稱，functionName
    Map<Integer, EquipmentInfo> equipmentInfoMap = this.organizationUtilService.getFunctionNameMapByEquipmentIds(
        equipmentIds);

    //取得總投入時間
    List<TjOeeEntity> tjOeeList = this.tjOeeDao.findByShiftDateAndShiftAndEquipmentIdIn(
        queryDate, shiftEnum.getCode(), equipmentIds);
    Map<Integer, TjOeeEntity> tjOeeMap = tjOeeList.stream()
        .collect(Collectors.toMap(e -> e.getEquipmentId(), Function.identity()));

    //組成output
    List<GetSingleDatedErrorKpisOutput.Kpis> kpis = new ArrayList<>();
    stopInfoList.forEach(i -> {
      int equipmentId = (int) i[0];
      long errorCount = (long) i[1];
      double errorTotalTime = (double) i[2];
      String equipmentName = equipmentInfoMap.get(equipmentId).getEquipmentName();
      String lineName = equipmentInfoMap.get(equipmentId).getLineName();
      String functionName = equipmentInfoMap.get(equipmentId).getFunctionName();
      double totalTime = tjOeeMap.get(equipmentId).getTotalTime();
      double mttr = 0;
      double mtbf = 0;
      if (errorCount > 0) {
        mttr = errorTotalTime / errorCount;
        mtbf = (totalTime - errorTotalTime) / errorCount;
      }
      GetSingleDatedErrorKpisOutput.Kpis kpi = GetSingleDatedErrorKpisOutput.Kpis.builder()
          .equipmentId(equipmentId)
          .equipmentName(equipmentName)
          .lineName(lineName)
          .functionName(functionName)
          .errorCount(errorCount)
          .errorTotalTime(errorTotalTime)
          .totalTime(totalTime)
          .mttr(mttr)
          .mtbf(mtbf)
          .build();
      kpis.add(kpi);
    });

    GetSingleDatedErrorKpisOutput result = GetSingleDatedErrorKpisOutput.builder()
        .date(queryDate).kpis(kpis).build();

    return result;
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetMultiDatedErrorKpisOutput {

    /**
     * 設備編號
     */
    private int equipmentId;
    /**
     * 設備名稱
     */
    private String equipmentName;
    /**
     * 所屬line名稱
     */
    private String lineName;
    /**
     * 所屬function名稱
     */
    private String functionName;
    /**
     * KPI資料
     */
    private List<GetMultiDatedErrorKpisOutput.Kpis> kpis;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Kpis {

      /**
       * 日期
       */
      private LocalDate date;
      /**
       * 故障次數
       */
      private Long errorCount;
      /**
       * 故障總時間(秒)
       */
      private Double errorTotalTime;
      /**
       * 總投入時間(秒)
       */
      private Double totalTime;
      /**
       * 平均修復時間(秒)
       */
      private Double mttr;
      /**
       * 平均故障間隔(秒)
       */
      private Double mtbf;
    }
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetSingleDatedErrorKpisOutput {

    /**
     * 日期
     */
    private LocalDate date;
    /**
     * KPI資料
     */
    private List<Kpis> kpis;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Kpis {

      /**
       * 設備編號
       */
      private int equipmentId;
      /**
       * 設備名稱
       */
      private String equipmentName;
      /**
       * 所屬line名稱
       */
      private String lineName;
      /**
       * 所屬function名稱
       */
      private String functionName;
      /**
       * 故障次數
       */
      private Long errorCount;
      /**
       * 故障總時間(秒)
       */
      private Double errorTotalTime;
      /**
       * 總投入時間(秒)
       */
      private Double totalTime;
      /**
       * 平均修復時間(秒)
       */
      private Double mttr;
      /**
       * 平均故障間隔(秒)
       */
      private Double mtbf;
    }

  }

}
