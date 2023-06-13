package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.entity.ods.LogTjOeeEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.PzLineEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.TjOeeEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.LogTjOeeDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.PzLineDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.TjOeeDao;
import com.fitfoxconn.npi.dmp.api.service.PerformanceKpisService.GetMultiDatedPerformanceKpisOutput.Kpis;
import com.fitfoxconn.npi.dmp.api.service.common.KpiUtil;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService.EquipmentInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 取得Performance相關KPI的service
 */
@Service
public class PerformanceKpisService {

  @Autowired
  private TjOeeDao tjOeeDao;
  @Autowired
  private OrganizationUtilService organizationUtilService;
  @Autowired
  private PzLineDao pzLineDao;

  @Autowired
  private LogTjOeeDao logTjOeeDao;


  /**
   * 抓取單一設備，多天的效率相關KPI
   */
  public GetMultiDatedPerformanceKpisOutput getMultiDatedPerformanceKpis(int equipmentId,
      LocalDate startDate, LocalDate endDate, ShiftEnum shiftEnum) {

    //取得效率相關KPI資訊
    List<TjOeeEntity> tjOeeList = this.tjOeeDao.findByEquipmentIdAndShiftAndShiftDateBetween(
        equipmentId, shiftEnum.getCode(), startDate, endDate);

    //取得設備資訊，如設備名稱，functionName
    EquipmentInfo equipmentInfo = this.organizationUtilService.getFunctionNameMapByEquipmentId(
        equipmentId);

    //取得良率目標 from pz_Line
    PzLineEntity pzLineEntity = this.pzLineDao.findByLineOrganizationCode(
        equipmentInfo.getLineOrganizationCode());

    //組成output
    List<Kpis> kpis = new ArrayList<>();

    tjOeeList.forEach(i -> {
      Kpis kpi = new Kpis();
      BeanUtils.copyProperties(i, kpi);
      kpi.setDate(i.getShiftDate());
      kpi.setAchievementRate(KpiUtil.getAchievementRate(i));
      kpi.setQualityTarget(pzLineEntity.getQualityTarget());
      kpis.add(kpi);
    });

    GetMultiDatedPerformanceKpisOutput result = GetMultiDatedPerformanceKpisOutput.builder()
        .equipmentId(equipmentInfo.getEquipmentID())
        .equipmentName(equipmentInfo.getEquipmentName())
        .lineName(equipmentInfo.getLineName())
        .functionName(equipmentInfo.getFunctionName())
        .kpis(kpis)
        .build();

    return result;
  }

  /**
   * 抓取多個設備，某天的效率相關KPI
   */
  public GetSingleDatedPerformanceKpisOutput getSingleDatedPerformanceKpis(
      List<Integer> equipmentIds,
      LocalDate queryDate,
      ShiftEnum shiftEnum) {

    //取得效率相關KPI資訊
    List<TjOeeEntity> tjOeeList = this.tjOeeDao.findByShiftDateAndShiftAndEquipmentIdIn(queryDate,
        shiftEnum.getCode(), equipmentIds);

    //取得設備資訊，如設備名稱，functionName
    Map<Integer, EquipmentInfo> equipmentInfoMap = this.organizationUtilService.getFunctionNameMapByEquipmentIds(
        equipmentIds);

    //取得良率目標 from pz_Line
    List<String> LineOrganizationCodeList = equipmentInfoMap.values().stream()
        .map(EquipmentInfo::getLineOrganizationCode)
        .distinct()
        .collect(
            Collectors.toList());
    Map<String, PzLineEntity> lineMap = this.getPzLineEntityMap(LineOrganizationCodeList);

    //組成output
    List<GetSingleDatedPerformanceKpisOutput.Kpis> kpis = new ArrayList<>();
    tjOeeList.forEach(i -> {
      GetSingleDatedPerformanceKpisOutput.Kpis kpi = new GetSingleDatedPerformanceKpisOutput.Kpis();
      BeanUtils.copyProperties(i, kpi);
      kpi.setEquipmentName(equipmentInfoMap.get(i.getEquipmentId()).getEquipmentName());
      kpi.setLineName(equipmentInfoMap.get(i.getEquipmentId()).getLineName());
      kpi.setFunctionName(equipmentInfoMap.get(i.getEquipmentId()).getFunctionName());
      kpi.setAchievementRate(KpiUtil.getAchievementRate(i));
      kpi.setQualityTarget(
          lineMap.get(equipmentInfoMap.get(i.getEquipmentId()).getLineOrganizationCode())
              .getQualityTarget());
      kpis.add(kpi);
    });

    GetSingleDatedPerformanceKpisOutput result = GetSingleDatedPerformanceKpisOutput.builder()
        .date(queryDate)
        .kpis(kpis)
        .build();

    return result;
  }

  /**
   * 抓取某一天，依課別區分，多個設備的效率相關KPI
   */
  public List<GetSingleDatedClassedPerformanceKpisOutput> getSingleDatedClassedPerformanceKpis(
      List<Integer> equipmentIds,
      LocalDate queryDate,
      List<LocalTime> boundaryTimes) {

    //取得效率相關KPI資訊
    Map<LocalTime, List<LogTjOeeEntity>> logTjOeeEntitiesMap = new HashMap<>();
    boundaryTimes.forEach(t -> {
      LocalDateTime boundaryDateTime = queryDate.atTime(t);
      List<LogTjOeeEntity> logTjOeeEntities = this.logTjOeeDao.getLatestByShiftDateAndTimeAndEquipmentIdIn(
          queryDate, boundaryDateTime, equipmentIds);
      logTjOeeEntitiesMap.put(t, logTjOeeEntities);
    });

    //取得設備資訊，如設備名稱，functionName
    Map<Integer, EquipmentInfo> equipmentInfoMap = this.organizationUtilService.getFunctionNameMapByEquipmentIds(
        equipmentIds);

    //取得良率目標 from pz_Line
    List<String> LineOrganizationCodeList = equipmentInfoMap.values().stream()
        .map(EquipmentInfo::getLineOrganizationCode)
        .distinct()
        .collect(
            Collectors.toList());
    Map<String, PzLineEntity> lineMap = this.getPzLineEntityMap(LineOrganizationCodeList);

    //組成output
    List<GetSingleDatedClassedPerformanceKpisOutput> result = new ArrayList<>();

    logTjOeeEntitiesMap.forEach((k, v) -> {
      List<GetSingleDatedClassedPerformanceKpisOutput.Kpis> kpis = new ArrayList<>();
      v.forEach(i -> {
        GetSingleDatedClassedPerformanceKpisOutput.Kpis kpi = new GetSingleDatedClassedPerformanceKpisOutput.Kpis();
        BeanUtils.copyProperties(i, kpi);
        kpi.setEquipmentName(equipmentInfoMap.get(i.getEquipmentId()).getEquipmentName());
        kpi.setLineName(equipmentInfoMap.get(i.getEquipmentId()).getLineName());
        kpi.setFunctionName(equipmentInfoMap.get(i.getEquipmentId()).getFunctionName());
        kpi.setAchievementRate(KpiUtil.getAchievementRate(i));
        kpi.setQualityTarget(
            lineMap.get(equipmentInfoMap.get(i.getEquipmentId()).getLineOrganizationCode())
                .getQualityTarget());
        kpis.add(kpi);
      });

      GetSingleDatedClassedPerformanceKpisOutput output = GetSingleDatedClassedPerformanceKpisOutput.builder()
          .date(queryDate)
          .time(k)
          .kpis(kpis)
          .build();
      result.add(output);
    });

    return result;
  }

  /**
   * 取得以LineOrganizationCode為Key, PzLineEntity為value的Map
   */
  private Map<String, PzLineEntity> getPzLineEntityMap(List<String> lineOrganizationCode) {
    List<PzLineEntity> pzLineEntityList = this.pzLineDao.findByLineOrganizationCodeIn(
        lineOrganizationCode);
    Map<String, PzLineEntity> lineMap = pzLineEntityList.stream()
        .collect(Collectors.toMap(PzLineEntity::getLineOrganizationCode, Function.identity()));
    return lineMap;
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetMultiDatedPerformanceKpisOutput {

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
    private List<Kpis> kpis;

    @lombok.Data
    public static class Kpis {

      /**
       * 日期
       */
      private LocalDate date;
      /**
       * 計畫量(pcs)
       */
      private Integer plannedProduction;
      /**
       * 產出目標(pcs)
       */
      private Integer productionTarget;
      /**
       * 實際產出(pcs)
       */
      private Integer totalCount;
      /**
       * 良品數(pcs)
       */
      private Integer goodCount;
      /**
       * 良率目標(%)
       */
      private Double qualityTarget;
      /**
       * 良率實際(%)
       */
      private BigDecimal quality;
      /**
       * 達成率(%)
       */
      private BigDecimal achievementRate;
      /**
       * 總投入時間(秒)
       */
      private Double totalTime;
      /**
       * 宕機時間(秒)
       */
      private Double unplannedStopTime;
      /**
       * 計畫停機時間(秒)
       */
      private Double plannedStopTime;
      /**
       * 負荷時間(秒)
       */
      private Double plannedProductionTime;
      /**
       * 稼動時間(秒)
       */
      private Double runTime;
      /**
       * 時間稼動率(%)
       */
      private BigDecimal availability;
      /**
       * 理論週期
       */
      private Double idealCycleTime;
      /**
       * 性能稼動率(%)
       */
      private BigDecimal performance;
      /**
       * OEE1
       */
      private BigDecimal oee1;
      /**
       * OEE
       */
      private BigDecimal oee;
    }
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetSingleDatedPerformanceKpisOutput {

    /**
     * 日期
     */
    private LocalDate date;
    /**
     * KPI資料
     */
    private List<Kpis> kpis;

    @lombok.Data
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
       * 計畫量(pcs)
       */
      private Integer plannedProduction;
      /**
       * 產出目標(pcs)
       */
      private Integer productionTarget;
      /**
       * 實際產出(pcs)
       */
      private Integer totalCount;
      /**
       * 良品數(pcs)
       */
      private Integer goodCount;
      /**
       * 良率目標(%)
       */
      private Double qualityTarget;
      /**
       * 良率實際(%)
       */
      private BigDecimal quality;
      /**
       * 達成率(%)
       */
      private BigDecimal achievementRate;
      /**
       * 總投入時間(秒)
       */
      private Double totalTime;
      /**
       * 宕機時間(秒)
       */
      private Double unplannedStopTime;
      /**
       * 計畫停機時間(秒)
       */
      private Double plannedStopTime;
      /**
       * 負荷時間(秒)
       */
      private Double plannedProductionTime;
      /**
       * 稼動時間(秒)
       */
      private Double runTime;
      /**
       * 時間稼動率(%)
       */
      private BigDecimal availability;
      /**
       * 理論週期
       */
      private Double idealCycleTime;
      /**
       * 性能稼動率(%)
       */
      private BigDecimal performance;
      /**
       * OEE1
       */
      private BigDecimal oee1;
      /**
       * OEE
       */
      private BigDecimal oee;
    }
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetSingleDatedClassedPerformanceKpisOutput {

    /**
     * 日期
     */
    private LocalDate date;

    private LocalTime time;
    /**
     * KPI資料
     */
    private List<Kpis> kpis;

    @lombok.Data
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
       * 計畫量(pcs)
       */
      private Integer plannedProduction;
      /**
       * 產出目標(pcs)
       */
      private Integer productionTarget;
      /**
       * 實際產出(pcs)
       */
      private Integer totalCount;
      /**
       * 良品數(pcs)
       */
      private Integer goodCount;
      /**
       * 良率目標(%)
       */
      private Double qualityTarget;
      /**
       * 良率實際(%)
       */
      private BigDecimal quality;
      /**
       * 達成率(%)
       */
      private BigDecimal achievementRate;
      /**
       * 總投入時間(秒)
       */
      private Double totalTime;
      /**
       * 宕機時間(秒)
       */
      private Double unplannedStopTime;
      /**
       * 計畫停機時間(秒)
       */
      private Double plannedStopTime;
      /**
       * 負荷時間(秒)
       */
      private Double plannedProductionTime;
      /**
       * 稼動時間(秒)
       */
      private Double runTime;
      /**
       * 時間稼動率(%)
       */
      private BigDecimal availability;
      /**
       * 理論週期
       */
      private Double idealCycleTime;
      /**
       * 性能稼動率(%)
       */
      private BigDecimal performance;
      /**
       * OEE1
       */
      private BigDecimal oee1;
      /**
       * OEE
       */
      private BigDecimal oee;
    }
  }

}
