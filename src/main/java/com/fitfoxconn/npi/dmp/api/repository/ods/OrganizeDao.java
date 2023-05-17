package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fitfoxconn.npi.dmp.api.service.model.LineOrganization;

import java.util.List;

/**
 * Organize dao
 */
@Repository
public interface OrganizeDao extends JpaRepository<OrganizeEntity, Integer> {

  /**
   * @param fatherId
   * @return OrganizationEntity
   */
  public List<OrganizeEntity> findByFatherId(int fatherId);

  /**
   * 用line的organization code查詢所屬的function name
   *
   * @param lineOrganizationCodeList line的organization code list
   * @return {@code List<[line的organization code, 所屬function的名稱]>}
   */

  @Query(value =
      "SELECT new com.fitfoxconn.npi.dmp.api.service.model.LineOrganization(line_o.notePath, line_o.alias, product_o.notePath, product_o.alias, function_o.notePath, function_o.alias) "
          + " from OrganizeEntity line_o, OrganizeEntity product_o, OrganizeEntity function_o "
          + " where line_o.notePath in ?1 "
          + "   and product_o.id = line_o.fatherId "
          + "   and function_o.id = product_o.fatherId ")
  public List<LineOrganization> getOrganizationByLines(
      List<String> lineOrganizationCodeList);

  /**
   * 透過上層的組織碼，抓取組織資料
   * @param parentCode 上層組織的組織碼
   * @return OrganizationEntity
   */
  @Query(value = "select o2 from OrganizeEntity o2 "
      + "right join OrganizeEntity o1 on o2.fatherId = o1.id "
      + "where o1.notePath = ?1")
  public List<OrganizeEntity> getByParentCode(String parentCode);


  /**
   * 透過Build層的組織碼，抓取Active的下一層組織資料
   * @param buildCode Build層組織的組織碼
   * @return 各層組織的組織碼與名稱(Build, Function, Produce, Line, Equipment)
   */
  @Query(value = "SELECT function_o.notePath, function_o.alias, function_o.orType "
      + " from EquipmentEntity e, OrganizeEntity line_o, OrganizeEntity product_o, OrganizeEntity function_o, OrganizeEntity build_o "
      + " where build_o.notePath = ?1 "
      + "   and function_o.fatherId = build_o.id "
      + "   and product_o.fatherId = function_o.id "
      + "   and line_o.fatherId = product_o.id "
      + "   and e.lineOrganizeCode = line_o.notePath "
      + "   and e.isShow = true"
      + " group by function_o.notePath, function_o.alias, function_o.orType ")
  public List<String[]> getActiveOrganizationByBuild(String buildCode);

  /**
   * 透過Function層的組織碼，抓取Active的下一層組織資料
   * @param functionCode Function層組織的組織碼
   * @return 各層組織的組織碼與名稱(Build, Function, Produce, Line, Equipment)
   */
  @Query(value = "SELECT product_o.notePath, product_o.alias, product_o.orType "
      + " from EquipmentEntity e, OrganizeEntity line_o, OrganizeEntity product_o, OrganizeEntity function_o "
      + " where function_o.notePath = ?1 "
      + "   and product_o.fatherId = function_o.id "
      + "   and line_o.fatherId = product_o.id "
      + "   and e.lineOrganizeCode = line_o.notePath "
      + "   and e.isShow = true"
      + " group by product_o.notePath, product_o.alias, product_o.orType ")
  public List<String[]> getActiveOrganizationByFunction(String functionCode);

  /**
   * 透過Product層的組織碼，抓取Active的下一層組織資料
   * @param productCode Product層組織的組織碼
   * @return 各層組織的組織碼與名稱(Build, Function, Produce, Line, Equipment)
   */
  @Query(value = "SELECT line_o.notePath, line_o.alias, line_o.orType "
      + " from EquipmentEntity e, OrganizeEntity line_o, OrganizeEntity product_o "
      + " where product_o.notePath = ?1 "
      + "   and line_o.fatherId = product_o.id "
      + "   and e.lineOrganizeCode = line_o.notePath "
      + "   and e.isShow = true"
      + " group by line_o.notePath, line_o.alias, line_o.orType ")
  public List<String[]> getActiveOrganizationByProduct(String productCode);

  /**
   * 透過組織碼，抓取Organize的資料
   * @param organizationCode 組織的組織碼
   * @return OrganizeEntity
   */
  public OrganizeEntity findByNotePath(String organizationCode);


}
