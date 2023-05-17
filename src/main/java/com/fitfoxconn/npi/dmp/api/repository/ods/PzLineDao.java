package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.PzLineEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * pz_line dao
 */
public interface PzLineDao extends JpaRepository<PzLineEntity, Integer> {

  /**
   * 查詢pz_Line資料
   *
   * @param lineOrganizationCodes line organization code list
   *
   * @return {@code List<PzLineEntity>}
   */
  public List<PzLineEntity> findByLineOrganizationCodeIn(List<String> lineOrganizationCodes);

  /**
   * 查詢pz_Line資料
   *
   * @param lineOrganizationCode line organization code
   *
   * @return PzLineEntity
   */
  public PzLineEntity findByLineOrganizationCode(String lineOrganizationCode);

}
