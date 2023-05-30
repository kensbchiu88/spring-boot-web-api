package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.OtOpenWorkOrderEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.OtOpenWorkOrderPk;
import com.fitfoxconn.npi.dmp.api.entity.ods.OtProductTransactionsEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OtProductTransactions dao
 */
@Repository
public interface OtProductTransactionsDao extends
    JpaRepository<OtProductTransactionsEntity, String> {

  /**
   * 抓取庫存明細
   *
   * @param partNumber  料號
   * @param startDate   查詢區間的開始日期
   * @param endDate     查詢區間的結束日期
   * @return OtProductTransactionsEntity
   */
  public List<OtProductTransactionsEntity> findByPartNumberAndSearchDateBetween(String partNumber,
      LocalDateTime startDate, LocalDateTime endDate);
}
