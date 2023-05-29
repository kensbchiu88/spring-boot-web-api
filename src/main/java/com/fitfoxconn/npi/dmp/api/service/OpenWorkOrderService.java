package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.repository.ods.OtOpenWorkOrderDao;
import com.fitfoxconn.npi.dmp.api.service.model.OpenWorkOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 未結工單service
 */
@Service
public class OpenWorkOrderService {

  @Autowired
  private OtOpenWorkOrderDao otOpenWorkOrderDao;

  /**
   * 抓取實際消耗率最高的未結工單資訊
   */
  public List<GetOpenWorkOrderByDateOutput> getOpenWorkOrderByDate(LocalDate startDate, LocalDate endDate) {

    List<OpenWorkOrder> workOrderEntityList = this.otOpenWorkOrderDao.findByShowDateBetween(
        startDate, endDate);

    List<GetOpenWorkOrderByDateOutput> result = new ArrayList<>();

    workOrderEntityList.forEach(i -> {
      GetOpenWorkOrderByDateOutput output = new GetOpenWorkOrderByDateOutput();
      BeanUtils.copyProperties(i, output);
      result.add(output);
    });

    return result;
  }

  @Data
  public static class GetOpenWorkOrderByDateOutput {
    /** 登記為未結工單的時間 */
    private LocalDate showDate;
    /** 取消為未結工單的時間 */
    private LocalDate hideDate;
    /** 庫存組織 */
    private String organizationName;
    /** 工單號 */
    private String workNumber;
    /** 類別 */
    private String classCode;
    /** 狀態 */
    private String status;
    /** 創建人 */
    private String creater;
    /** 裝配件料號 */
    private String assemblyPartNumber;
    /** 裝配件名稱 */
    private String assemblyPartName;
    /** 工單開始日期 */
    private LocalDateTime startDate;
    /** 計劃完工數量 */
    private Integer targetQuantity;
    /** 實際完工數量 */
    private Integer completedQuantity;
    /** 組件料號 */
    private String componentPartNumber;
    /** 組件名稱 */
    private String componentPartName;
    /** 單位 */
    private String unit;
    /** 單位用量 */
    private Double quantityPerAssembly;
    /** 組件良品率 */
    private Double componentYieldFactor;
    /** 需求數量 */
    private Double requiredQuantity;
    /** 實際發料數量 */
    private Double quantityIssued;
    /** 課別 */
    private String classId;
    /** 首次發料過帳日期 */
    private LocalDateTime programUpdateDate;
    /** 實際使用數量 */
    private BigDecimal QuantityUsed;
    /** 實際消耗數量 */
    private BigDecimal QuantityLoss;
    /** 實際消耗率 */
    private BigDecimal LossRatio;
    /** 工齡 */
    private Integer OrderAge;
  }
}
