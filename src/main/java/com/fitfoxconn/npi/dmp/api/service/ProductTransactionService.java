package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.entity.ods.OtProductTransactionsEntity;
import com.fitfoxconn.npi.dmp.api.repository.ods.OtProductTransactionsDao;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 產品庫存資訊 Service
 */

@Service
public class ProductTransactionService {

  @Autowired
  private OtProductTransactionsDao otProductTransactionsDao;

  /**
   * 抓取產品庫存明細
   */
  public List<getProductTransactionOutput> getProductTransaction(String partNumber,
      LocalDate startDate, LocalDate endDate) {
    List<OtProductTransactionsEntity> productTransactionsEntityList = this.otProductTransactionsDao.findByPartNumberAndSearchDateBetween(
        partNumber, startDate.atStartOfDay(), endDate.atStartOfDay());

    List<getProductTransactionOutput> result = new ArrayList<>();
    productTransactionsEntityList.forEach(i -> {
      getProductTransactionOutput output = new getProductTransactionOutput();
      BeanUtils.copyProperties(i, output);
      result.add(output);
    });
    return result;
  }

  @Data
  public static class getProductTransactionOutput {

    /**
     * 交易識別碼
     */
    private String transactionId;
    /**
     * 產品料號
     */
    private String partNumber;
    /**
     * 產品名稱
     */
    private String description;
    /**
     * 單位
     */
    private String unit;
    /**
     * 數量
     */
    private Double quantity;
    /**
     * 庫存組織代碼
     */
    private String subinventoryCode;
    /**
     * 查找日期
     */
    private LocalDateTime searchDate;
    /**
     * 更新時間
     */
    private LocalDateTime updateDate;
  }

}
