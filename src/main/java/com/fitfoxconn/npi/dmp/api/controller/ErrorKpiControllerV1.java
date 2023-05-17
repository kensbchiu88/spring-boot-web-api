package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.common.exception.ValidateException;
import com.fitfoxconn.npi.dmp.api.model.GetMultiDatedErrorKpisRs;
import com.fitfoxconn.npi.dmp.api.model.GetMultiDatedErrorKpisRs.Kpis;
import com.fitfoxconn.npi.dmp.api.model.GetSingleDatedErrorKpisRs;
import com.fitfoxconn.npi.dmp.api.service.ErrorKpisService;
import com.fitfoxconn.npi.dmp.api.service.ErrorKpisService.GetMultiDatedErrorKpisOutput;
import com.fitfoxconn.npi.dmp.api.service.ErrorKpisService.GetSingleDatedErrorKpisOutput;
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
 * 設備錯誤相關kpi controller
 */

@RestController
@RequestMapping("/api/v1/equipment")
public class ErrorKpiControllerV1 {

  @Autowired
  private ErrorKpisService errorKpisService;

  /**
   * 抓取單一設備，多天的錯誤相關KPI
   */
  @GetMapping(value = "/multi-dated-error-kpis")
  @Operation(summary = "抓取單一設備，多天的錯誤相關KPI", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentId", description = "設備代碼", schema = @Schema(example = "4661"))
  @Parameter(name = "startDate", description = "查詢日期起", schema = @Schema(example = "2023-05-11"))
  @Parameter(name = "endDate", description = "查詢日期訖", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetMultiDatedErrorKpisRs getMultiDatedErrorKpis(
      @RequestParam int equipmentId,
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam int shift) throws ValidateException {
    ShiftEnum shiftEnum = ShiftEnum.getByShiftCode(shift);
    GetMultiDatedErrorKpisOutput serviceOutput = this.errorKpisService.getMultiDatedErrorKpis(
        equipmentId, LocalDate.parse(startDate), LocalDate.parse(endDate), shiftEnum);

    List<Kpis> responseKpis = new ArrayList<>();

    serviceOutput.getKpis().forEach(i -> {
      GetMultiDatedErrorKpisRs.Kpis kpi = new GetMultiDatedErrorKpisRs.Kpis();
      BeanUtils.copyProperties(i, kpi);
      responseKpis.add(kpi);
    });

    GetMultiDatedErrorKpisRs response = GetMultiDatedErrorKpisRs.builder()
        .equipmentId(serviceOutput.getEquipmentId())
        .equipmentName(serviceOutput.getEquipmentName())
        .lineName(serviceOutput.getLineName())
        .functionName(serviceOutput.getFunctionName())
        .kpis(responseKpis)
        .build();

    return response;
  }

  /**
   * 抓取多個設備，某天的錯誤相關KPI
   */
  @GetMapping(value = "/single-dated-error-kpis")
  @Operation(summary = "抓取多個設備，某天的錯誤相關KPI", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentIds", description = "設備代碼", schema = @Schema(example = "4660, 4661"))
  @Parameter(name = "queryDate", description = "查詢日期", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetSingleDatedErrorKpisRs getSingleDatedErrorKpis(
      @RequestParam int[] equipmentIds,
      @RequestParam String queryDate,
      @RequestParam int shift) throws ValidateException {

    GetSingleDatedErrorKpisOutput serviceOutput = this.errorKpisService.getSingleDatedErrorKpis(
        Arrays.stream(equipmentIds).boxed().collect(Collectors.toList()),
        LocalDate.parse(queryDate), ShiftEnum.getByShiftCode(shift));

    List<GetSingleDatedErrorKpisRs.Kpis> responseKpis = new ArrayList<>();

    serviceOutput.getKpis().forEach(i -> {
      GetSingleDatedErrorKpisRs.Kpis kpi = new GetSingleDatedErrorKpisRs.Kpis();
      BeanUtils.copyProperties(i, kpi);
      responseKpis.add(kpi);
    });

    GetSingleDatedErrorKpisRs response = GetSingleDatedErrorKpisRs.builder()
        .date(LocalDate.parse(queryDate))
        .kpis(responseKpis).build();

    return response;

  }

}
