package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.common.constant.ShiftEnum;
import com.fitfoxconn.npi.dmp.api.common.exception.ValidationException;
import com.fitfoxconn.npi.dmp.api.model.GetMultiDatedPerformanceKpisRs;
import com.fitfoxconn.npi.dmp.api.model.GetMultiDatedPerformanceKpisRs.Kpis;
import com.fitfoxconn.npi.dmp.api.model.GetSingleDatedPerformanceKpisRs;
import com.fitfoxconn.npi.dmp.api.service.PerformanceKpisService;
import com.fitfoxconn.npi.dmp.api.service.PerformanceKpisService.GetMultiDatedPerformanceKpisOutput;
import com.fitfoxconn.npi.dmp.api.service.PerformanceKpisService.GetSingleDatedPerformanceKpisOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * 設備效率相關kpi controller
 */
@Tag(name = "PerformanceKpiControllerV1", description = "設備效率指標API")
@RestController
@RequestMapping("/api/v1/equipment")
public class PerformanceKpiControllerV1 {

  @Autowired
  PerformanceKpisService performanceKpisService;

  /**
   * 抓取單一設備，多天的效率相關KPI
   */
  @GetMapping(value = "/multi-dated-performance-kpis")
  @Operation(summary = "抓取單一設備，多天的效率相關KPI", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentId", description = "設備代碼", schema = @Schema(example = "4660"))
  @Parameter(name = "startDate", description = "查詢日期起", schema = @Schema(example = "2023-05-11"))
  @Parameter(name = "endDate", description = "查詢日期訖", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetMultiDatedPerformanceKpisRs getMultiDatedPerformanceKpis(
      @RequestParam int equipmentId,
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam int shift) throws ValidationException {

    ShiftEnum shiftEnum = ShiftEnum.getByShiftCode(shift);
    GetMultiDatedPerformanceKpisOutput serviceOutput = this.performanceKpisService.getMultiDatedPerformanceKpis(
        equipmentId, LocalDate.parse(startDate),
        LocalDate.parse(endDate), shiftEnum);

    List<GetMultiDatedPerformanceKpisRs.Kpis> responseKpis = new ArrayList<>();

    serviceOutput.getKpis().forEach(i -> {
      GetMultiDatedPerformanceKpisRs.Kpis kpi = new Kpis();
      BeanUtils.copyProperties(i, kpi);
      responseKpis.add(kpi);
    });

    GetMultiDatedPerformanceKpisRs response = GetMultiDatedPerformanceKpisRs.builder()
        .equipmentId(serviceOutput.getEquipmentId())
        .equipmentName(serviceOutput.getEquipmentName())
        .functionName(serviceOutput.getFunctionName())
        .kpis(responseKpis)
        .build();

    return response;
  }

  /**
   * 抓取某一天，多個設備的效率相關KPI
   */
  @GetMapping(value = "/single-dated-performance-kpis")
  @Operation(summary = "抓取某一天，多個設備的效率相關KPI", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "equipmentIds", description = "設備代碼", schema = @Schema(example = "4660, 4661"))
  @Parameter(name = "queryDate", description = "查詢日期", schema = @Schema(example = "2023-05-12"))
  @Parameter(name = "shift", description = "班別。1:早班，2:晚班", schema = @Schema(type = "string", example = "1"))
  public GetSingleDatedPerformanceKpisRs getSingleDatedPerformanceKpis(
      @RequestParam int[] equipmentIds,
      @RequestParam(value = "queryDate", required = true) String queryDate,
      @RequestParam int shift
  ) throws ValidationException {

    GetSingleDatedPerformanceKpisOutput serviceOutput = this.performanceKpisService.getSingleDatedPerformanceKpis(
        Arrays.stream(equipmentIds).boxed().collect(Collectors.toList()),
        LocalDate.parse(queryDate), ShiftEnum.getByShiftCode(shift));

    List<GetSingleDatedPerformanceKpisRs.Kpis> responseKpis = new ArrayList<>();

    serviceOutput.getKpis().forEach(i -> {
      GetSingleDatedPerformanceKpisRs.Kpis kpi = new GetSingleDatedPerformanceKpisRs.Kpis();
      BeanUtils.copyProperties(i, kpi);
      responseKpis.add(kpi);
    });

    GetSingleDatedPerformanceKpisRs response = GetSingleDatedPerformanceKpisRs.builder()
        .date(LocalDate.parse(queryDate))
        .kpis(responseKpis)
        .build();

    return response;

  }
}
