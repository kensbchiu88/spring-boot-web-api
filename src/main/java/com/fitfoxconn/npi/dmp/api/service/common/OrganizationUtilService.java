package com.fitfoxconn.npi.dmp.api.service.common;

import com.fitfoxconn.npi.dmp.api.entity.ods.EquipmentEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.EquipmentDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.OrganizeDao;
import com.fitfoxconn.npi.dmp.api.service.model.LineOrganization;
import java.util.Arrays;
import java.util.HashMap;
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
 * 組織相關的共用function
 */
@Service
public class OrganizationUtilService {

  @Autowired
  private EquipmentDao equipmentDao;
  @Autowired
  private OrganizeDao organizeDao;

  /**
   * 使用equipment id抓取equipment的資訊(含所屬function的名稱)
   *
   * @param equipmentIds equipment id list
   * @return {@code Map<equipmentId, equipment的資訊(含所屬function的名稱)>}
   */
  public Map<Integer, EquipmentInfo> getFunctionNameMapByEquipmentIds(List<Integer> equipmentIds) {
    List<EquipmentEntity> equipmentList = this.equipmentDao.findByIdIn(equipmentIds);

    Map<Integer, EquipmentEntity> equipmentMap = equipmentList.stream()
        .collect(Collectors.toMap(EquipmentEntity::getId, Function.identity()));

    List<String> lineOrganizationCodeList = equipmentList.stream().map(e -> e.getLineOrganizeCode())
        .collect(
            Collectors.toList());

    List<LineOrganization> lineOrganizationList = this.organizeDao.getOrganizationByLines(
        lineOrganizationCodeList);
    Map<String, LineOrganization> lineOrganizationMap = lineOrganizationList.stream()
        .collect(Collectors.toMap(LineOrganization::getLineOrganizationCode, Function.identity()));

    Map<Integer, EquipmentInfo> result = new HashMap<>();

    equipmentIds.stream().forEach(e -> {
      if (!result.containsKey(e)) {
        LineOrganization lineOrganization = lineOrganizationMap.get(equipmentMap.get(e).getLineOrganizeCode());
        EquipmentInfo info = EquipmentInfo.builder()
            .equipmentID(e)
            .equipmentName(equipmentMap.get(e).getEquipmentName())
            .equipmentOrganizationCode(equipmentMap.get(e).getLineOrganizeCode())
            .lineOrganizationCode(lineOrganization.getLineOrganizationCode())
            .lineName(lineOrganization.getLineName())
            .productOrganizationCode(lineOrganization.getProductOrganizationCode())
            .productName(lineOrganization.getProductName())
            .functionOrganizationCode(lineOrganization.getFunctionOrganizationCode())
            .functionName(lineOrganization.getFunctionName())
            .build();
        result.put(e, info);
      }
    });

    return result;
  }

  /**
   * 使用equipment id抓取equipment的資訊(含所屬function的名稱)
   *
   * @param equipmentId equipment
   * @return equipment的資訊(含所屬function的名稱)
   */
  public EquipmentInfo getFunctionNameMapByEquipmentId(Integer equipmentId) {
    return this.getFunctionNameMapByEquipmentIds(Arrays.asList(equipmentId)).get(equipmentId);
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class EquipmentInfo {

    /**
     * 設備編號
     */
    private int equipmentID;
    /**
     * 設備名稱
     */
    private String equipmentName;
    /**
     * 設備的organization code
     */
    private String equipmentOrganizationCode;

    /**
     * 設備所屬線別的organization code
     */
    private String lineOrganizationCode;

    /**
     * 設備所屬線別名稱
     */
    private String lineName;
    /**
     * 所屬產品的組織碼
     */
    private String productOrganizationCode;
    /**
     * 設備所屬產品名稱
     */
    private String productName;
    /**
     * 所屬function的組織碼
     */
    private String functionOrganizationCode;
    /**
     * 設備所屬function名稱
     */
    private String functionName;
  }
}
