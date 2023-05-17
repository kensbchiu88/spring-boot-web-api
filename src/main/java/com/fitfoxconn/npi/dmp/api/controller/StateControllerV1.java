package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.common.exception.ValidateException;
import com.fitfoxconn.npi.dmp.api.model.GetCurrentStateRs;
import com.fitfoxconn.npi.dmp.api.model.GetCurrentStateRs.EquipmentState;
import com.fitfoxconn.npi.dmp.api.model.GetStatesRs;
import com.fitfoxconn.npi.dmp.api.model.GetStatesRs.EquipmentStateList;
import com.fitfoxconn.npi.dmp.api.model.GetStatesRs.State;
import com.fitfoxconn.npi.dmp.api.service.EquipmentStateService;
import com.fitfoxconn.npi.dmp.api.service.EquipmentStateService.GetCurrentStateOutput;
import com.fitfoxconn.npi.dmp.api.service.EquipmentStateService.GetStatesOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 設備狀態相關API controller
 */
@RestController
@RequestMapping("/api/v1/equipment")
public class StateControllerV1 {

  @Autowired
  private EquipmentStateService equipmentStateService;

  /**
   * 抓取設備當下狀態
   */
  @GetMapping(value = "/current-state")
  @Operation(summary = "抓取設備當下狀態", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentIds", description = "設備代碼", schema = @Schema(example = "4660, 4661"))
  @Parameter(name = "queryDate", description = "查詢日期", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetCurrentStateRs getCurrentState(
      @RequestParam int[] equipmentIds,
      @RequestParam String queryDate,
      @RequestParam int shift) throws ValidateException {

    GetCurrentStateOutput serviceOutput = this.equipmentStateService.getCurrentState(
        Arrays.stream(equipmentIds).boxed().collect(Collectors.toList()),
        LocalDate.parse(queryDate), ShiftEnum.getByShiftCode(shift));

    //將service的output轉換成response
    List<EquipmentState> responseStates = new ArrayList<>();

    serviceOutput.getStates().stream().forEach(s -> {
      GetCurrentStateRs.EquipmentState state = new GetCurrentStateRs.EquipmentState();
      BeanUtils.copyProperties(s, state);
      state.setStateCode(s.getErrorCodeId());
      responseStates.add(state);
    });

    GetCurrentStateRs response = GetCurrentStateRs.builder()
        .date(LocalDate.parse(queryDate))
        .states(responseStates).build();

    return response;
  }

  /**
   * 抓取設備的歷史狀態
   */
  @GetMapping(value = "/states")
  @Operation(summary = "抓取設備的歷史狀態", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentIds", description = "設備代碼", schema = @Schema(example = "4660, 4661"))
  @Parameter(name = "queryDate", description = "查詢日期", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetStatesRs getStates(
      @RequestParam int[] equipmentIds,
      @RequestParam String queryDate,
      @RequestParam int shift) throws ValidateException {

    GetStatesOutput serviceOutput = this.equipmentStateService.getStates(
        Arrays.stream(equipmentIds).boxed().collect(Collectors.toList()),
        LocalDate.parse(queryDate), ShiftEnum.getByShiftCode(shift));

    //將service的output轉換成response
    List<GetStatesRs.EquipmentStateList> responseEquipmentStates = new ArrayList<>();

    for (GetStatesOutput.EquipmentStateList s1 : serviceOutput.getEquipmentStates()) {
      List<State> responseStates = new ArrayList<>();
      s1.getStates().stream().forEach(s2 -> {
        GetStatesRs.State state = new State();
        BeanUtils.copyProperties(s2, state);
        state.setStateCode(s2.getErrorCodeId());
        responseStates.add(state);
      });
      GetStatesRs.EquipmentStateList responseEquipmentState = new EquipmentStateList();
      BeanUtils.copyProperties(s1, responseEquipmentState);
      responseEquipmentState.setStates(responseStates);
      responseEquipmentStates.add(responseEquipmentState);
    }

    GetStatesRs response = GetStatesRs.builder()
        .date(LocalDate.parse(queryDate))
        .EquipmentStates(responseEquipmentStates)
        .build();

    return response;
  }


}
