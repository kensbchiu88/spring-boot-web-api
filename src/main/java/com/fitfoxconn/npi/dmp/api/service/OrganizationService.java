package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.entity.ods.EquipmentEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.PzLineEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.EquipmentDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.OrganizeDao;
import com.fitfoxconn.npi.dmp.api.repository.ods.PzLineDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 取得組織資訊service
 */
@Service
public class OrganizationService {

  @Autowired
  private OrganizeDao organizeDao;

  @Autowired
  private PzLineDao pzLineDao;

  @Autowired
  private EquipmentDao equipmentDao;

  /**
   * 取得下層組織資訊(不含設備)
   */
  public List<GetOrganizationCodeNameOutput> getOrganization(String parentCode) {

    List<OrganizeEntity> organizeEntities = this.organizeDao.getByParentCode(parentCode);

    List<GetOrganizationCodeNameOutput> result = new ArrayList<>();
    organizeEntities.stream().forEach(o -> {
      result.add(GetOrganizationCodeNameOutput.builder().code(o.getNotePath()).name(o.getAlias())
          .type(o.getOrType()).build());
    });
    return result;
  }

  /**
   * 取得下層Active的組織資訊(不含設備)
   */
  public List<GetOrganizationCodeNameOutput> getActiveOrganization(String parentCode) {
    OrganizeEntity organizeEntity = this.organizeDao.findByNotePath(parentCode);
    List<GetOrganizationCodeNameOutput> result;
    switch (organizeEntity.getOrType()) {
      case "Build":
        result = this.transform(this.organizeDao.getActiveOrganizationByBuild(parentCode));
        break;
      case "Function":
        result = this.transform(this.organizeDao.getActiveOrganizationByFunction(parentCode));
        break;
      case "Product":
        result = this.transform(this.organizeDao.getActiveOrganizationByProduct(parentCode));
        break;
      default:
        result = new ArrayList<>();
    }
    return result;
  }

  /**
   * 將資料轉換成Output DTO
   */
  private List<GetOrganizationCodeNameOutput> transform(List<String[]> input) {
    List<GetOrganizationCodeNameOutput> result = new ArrayList<>();
    input.stream().forEach(i -> {
      result.add(GetOrganizationCodeNameOutput.builder().code(i[0]).name(i[1]).type(i[2]).build());
    });
    return result;
  }

  /**
   * 抓取線別的關鍵設備
   */
  public List<GetEquipmentByLineOutput> getKeyEquipmentByLines(List<String> lineCodeList) {
    List<PzLineEntity> lineEntities = this.pzLineDao.findByLineOrganizationCodeIn(lineCodeList);
    List<GetEquipmentByLineOutput> result = new ArrayList<>();

    lineEntities.stream().forEach(i -> {
      result.add(GetEquipmentByLineOutput.builder().lineCode(i.getLineOrganizationCode())
          .equipmentId(i.getKeyEquipmentId()).build());
    });

    return result;
  }

  /**
   * 抓取線別下的所有設備
   */
  public List<GetEquipmentsByLineOutput> getEquipmentsByLine(List<String> lineCodeList) {
    List<EquipmentEntity> equipmentEntities = this.equipmentDao.findByIsShowTrueAndLineOrganizeCodeInOrderByLineOrganizeCode(
        lineCodeList);
    Map<String, List<Integer>> tempMap = new HashMap<>();

    equipmentEntities.stream().forEach(i -> {
      if (!tempMap.containsKey(i.getLineOrganizeCode())) {
        List<Integer> valueList = new ArrayList<>();
        tempMap.put(i.getLineOrganizeCode(), valueList);
      }
      tempMap.get(i.getLineOrganizeCode()).add(i.getId());
    });

    List<GetEquipmentsByLineOutput> result = new ArrayList<>();
    tempMap.forEach((k, v) -> {
      result.add(GetEquipmentsByLineOutput.builder().lineCode(k).equipmentIds(v).build());
    });

    return result;
  }


  @Builder
  @Data
  public static class GetOrganizationCodeNameOutput {

    /**
     * 組織碼
     */
    private String code;
    /**
     * 組織名稱
     */
    private String name;
    /**
     * 組織類型
     */
    private String type;
  }

  @Builder
  @Data
  public static class GetEquipmentByLineOutput {

    /**
     * 線別組織碼
     */
    private String lineCode;
    /**
     * 設備代碼
     */
    private Integer equipmentId;
  }

  @Builder
  @Data
  public static class GetEquipmentsByLineOutput {

    /**
     * 線別組織碼
     */
    private String lineCode;
    /**
     * 設備代碼
     */
    private List<Integer> equipmentIds;
  }

}
