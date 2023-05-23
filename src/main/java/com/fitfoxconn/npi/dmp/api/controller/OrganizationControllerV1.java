package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetEquipmentsRs;
import com.fitfoxconn.npi.dmp.api.model.GetKeyEquipmentRs;
import com.fitfoxconn.npi.dmp.api.model.GetOrganizationCodeNameRs;
import com.fitfoxconn.npi.dmp.api.service.OrganizationService;
import com.fitfoxconn.npi.dmp.api.service.OrganizationService.GetEquipmentByLineOutput;
import com.fitfoxconn.npi.dmp.api.service.OrganizationService.GetEquipmentsByLineOutput;
import com.fitfoxconn.npi.dmp.api.service.OrganizationService.GetOrganizationCodeNameOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 組織相關API controller
 */

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationControllerV1 {

  @Autowired
  private OrganizationService organizationService;

  @GetMapping(value = "/child-organizations")
  @Operation(summary = "取得下層組織資訊(不含設備)", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "parentCode", description = "上層組織碼", schema = @Schema(example = "0602"))
  public List<GetOrganizationCodeNameRs> getChildOrganization(@RequestParam String parentCode) {
    List<GetOrganizationCodeNameOutput> serviceOutput = this.organizationService.getOrganization(
        parentCode);

    List<GetOrganizationCodeNameRs> response = new ArrayList<>();
    serviceOutput.stream().forEach(o -> {
      response.add(
          GetOrganizationCodeNameRs.builder().code(o.getCode()).name(o.getName()).type(o.getType())
              .build());
    });

    return response;
  }

  @GetMapping(value = "/active-child-organizations")
  @Operation(summary = "取得Active的下層組織資訊(不含設備)", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "parentCode", description = "上層組織碼", schema = @Schema(example = "0602"))
  public List<GetOrganizationCodeNameRs> getActiveChildOrganization(
      @RequestParam String parentCode) {
    List<GetOrganizationCodeNameOutput> serviceOutput = this.organizationService.getActiveOrganization(
        parentCode);

    List<GetOrganizationCodeNameRs> response = new ArrayList<>();
    serviceOutput.stream().forEach(o -> {
      response.add(
          GetOrganizationCodeNameRs.builder().code(o.getCode()).name(o.getName()).type(o.getType())
              .build());
    });

    return response;
  }

  @GetMapping(value = "/line/key-equipment")
  @Operation(summary = "取得線別的關鍵設備", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "lineCodes", description = "線別組織碼", schema = @Schema(example = "0602010502, 0602010502"))
  public List<GetKeyEquipmentRs> getKeyEquipment(@RequestParam String[] lineCodes) {
    List<GetEquipmentByLineOutput> serviceOutput = this.organizationService.getKeyEquipmentByLines(
        Arrays.stream(lineCodes).collect(Collectors.toList()));

    List<GetKeyEquipmentRs> response = new ArrayList<>();
    serviceOutput.stream().forEach(i -> {
      response.add(
          GetKeyEquipmentRs.builder().lineCode(i.getLineCode()).equipmentId(i.getEquipmentId())
              .build());
    });
    return response;
  }

  @GetMapping(value = "/line/equipments")
  @Operation(summary = "取得線別的所有設備", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "lineCodes", description = "線別組織碼", schema = @Schema(example = "0602010502, 0602010502"))
  public List<GetEquipmentsRs> getEquipments(@RequestParam String[] lineCodes) {

    List<GetEquipmentsByLineOutput> serviceOutput = this.organizationService.getEquipmentsByLine(
        Arrays.stream(lineCodes).collect(Collectors.toList()));

    List<GetEquipmentsRs> response = new ArrayList<>();
    serviceOutput.stream().forEach(i -> {
      response.add(GetEquipmentsRs.builder().lineCode(i.getLineCode()).equipmentIds(i.getEquipmentIds()).build());
    });

    return response;
  }

}
