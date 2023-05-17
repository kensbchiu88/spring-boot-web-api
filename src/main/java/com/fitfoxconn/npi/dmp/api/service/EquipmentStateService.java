package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.entity.ods.EquipmentStopPageEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.EquipmentStopPageDao;
import com.fitfoxconn.npi.dmp.api.service.EquipmentStateService.GetCurrentStateOutput.EquipmentState;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService;
import com.fitfoxconn.npi.dmp.api.service.common.OrganizationUtilService.EquipmentInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 取得設備狀態service
 */
@Service
public class EquipmentStateService {

  @Autowired
  private EquipmentStopPageDao equipmentStopPageDao;

  @Autowired
  private OrganizationUtilService organizationUtilService;

  /**
   * 抓取設備最新狀態資料(最後一筆)
   */
  public GetCurrentStateOutput getCurrentState(List<Integer> equipmentIds,
      LocalDate queryDate,
      ShiftEnum shiftEnum) {

    //取得EquipmentStoppage資訊
    List<EquipmentStopPageEntity> stopPageEntities = this.equipmentStopPageDao.getCurrentByShiftDateAndShiftAndEquipmentIdIn(
        queryDate, shiftEnum.getCode(), equipmentIds);

    //取得設備資訊，如設備名稱，functionName
    Map<Integer, EquipmentInfo> equipmentInfoMap = this.organizationUtilService.getFunctionNameMapByEquipmentIds(
        equipmentIds);

    //組成output
    List<GetCurrentStateOutput.EquipmentState> states = new ArrayList<>();
    stopPageEntities.forEach(s -> {
      GetCurrentStateOutput.EquipmentState state = new EquipmentState();
      BeanUtils.copyProperties(s, state);
      state.setEquipmentName(equipmentInfoMap.get(s.getEquipmentId()).getEquipmentName());
      state.setLineName(equipmentInfoMap.get(s.getEquipmentId()).getLineName());
      state.setFunctionName(equipmentInfoMap.get(s.getEquipmentId()).getFunctionName());
      states.add(state);
    });

    GetCurrentStateOutput result = GetCurrentStateOutput.builder()
        .date(queryDate)
        .states(states)
        .build();

    return result;
  }

  /**
   * 抓取設備的歷史狀態
   */
  public GetStatesOutput getStates(List<Integer> equipmentIds,
      LocalDate queryDate,
      ShiftEnum shiftEnum) {

    //取得EquipmentStoppage資料
    List<EquipmentStopPageEntity> stopPageEntities = this.equipmentStopPageDao.getByCreateTimeAndShiftAndEquipmentIdIn(
        queryDate, shiftEnum.getCode(), equipmentIds);

    //取得設備資訊，如設備名稱，functionName
    Map<Integer, EquipmentInfo> equipmentInfoMap = this.organizationUtilService.getFunctionNameMapByEquipmentIds(
        equipmentIds);

    //組成output
    GetStatesOutput result = GetStatesOutput.builder()
        .date(queryDate)
        .EquipmentStates(this.transform(stopPageEntities, equipmentInfoMap))
        .build();

    return result;
  }

  /**
   * 將EquipmentStopPageEntity轉換成output格式
   */
  private List<GetStatesOutput.EquipmentStateList> transform(List<EquipmentStopPageEntity> input,
      Map<Integer, EquipmentInfo> equipmentInfoMap) {
    List<GetStatesOutput.EquipmentStateList> result = new ArrayList<>();
    Map<Integer, Integer> equipmentIdMap = new HashMap<>();
    List<GetStatesOutput.State> states = new ArrayList<>();
    int equipmentId = 0;
    for (EquipmentStopPageEntity i : input) {
      //遇到新的equipment id, 將之前State(第二層array)加入EquipmentStateList(第一層array)
      if (!equipmentIdMap.containsKey(i.getEquipmentId())) {
        if (!states.isEmpty()) {
          GetStatesOutput.EquipmentStateList equipmentStates = GetStatesOutput.EquipmentStateList.builder()
              .equipmentId(equipmentId)
              .equipmentName(equipmentInfoMap.get(equipmentId).getEquipmentName())
              .lineName(equipmentInfoMap.get(equipmentId).getLineName())
              .functionName(equipmentInfoMap.get(equipmentId).getFunctionName())
              .states(states)
              .build();
          result.add(equipmentStates);
          states = new ArrayList<>();
        }
        equipmentIdMap.put(i.getEquipmentId(), i.getEquipmentId());
      }
      //將資料加入State(第二層array)
      GetStatesOutput.State state = new GetStatesOutput.State();
      BeanUtils.copyProperties(i, state);
      states.add(state);
      equipmentId = i.getEquipmentId();
    }
    //將最後的資料加入EquipmentStateList(第一層array)
    if (!states.isEmpty()) {
      GetStatesOutput.EquipmentStateList equipmentStates = GetStatesOutput.EquipmentStateList.builder()
          .equipmentId(equipmentId)
          .equipmentName(equipmentInfoMap.get(equipmentId).getEquipmentName())
          .lineName(equipmentInfoMap.get(equipmentId).getLineName())
          .functionName(equipmentInfoMap.get(equipmentId).getFunctionName())
          .states(states)
          .build();
      result.add(equipmentStates);
      states = new ArrayList<>();
    }
    return result;
  }


  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetCurrentStateOutput {

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 設備狀態
     */
    private List<EquipmentState> states;

    @Data
    public static class EquipmentState {

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
       * 狀態碼
       */
      private Integer errorCodeId;
      /**
       * 狀態名稱
       */
      private String stateName;
      /**
       * 開始時間
       */
      private LocalDateTime startTime;
      /**
       * 結束時間
       */
      private LocalDateTime endTime;
      /**
       * 持續時間(秒)
       */
      private Double duration;
    }
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class GetStatesOutput {

    /**
     * 日期
     */
    private LocalDate date;
    /**
     * 設備狀態
     */
    private List<EquipmentStateList> EquipmentStates;

    @Data
    @Builder
    public static class EquipmentStateList {

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
       * 設備狀態
       */
      private List<State> states;
    }

    @Data
    public static class State {

      /**
       * 狀態碼
       */
      private Integer errorCodeId;
      /**
       * 狀態名稱
       */
      private String stateName;
      /**
       * 開始時間
       */
      private LocalDateTime startTime;
      /**
       * 結束時間
       */
      private LocalDateTime endTime;
      /**
       * 持續時間(秒)
       */
      private Double duration;
    }

  }


}
