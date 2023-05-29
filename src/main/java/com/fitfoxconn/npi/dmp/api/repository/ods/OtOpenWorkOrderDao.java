package com.fitfoxconn.npi.dmp.api.repository.ods;

import com.fitfoxconn.npi.dmp.api.entity.ods.OtOpenWorkOrderEntity;
import com.fitfoxconn.npi.dmp.api.entity.ods.OtOpenWorkOrderPk;
import com.fitfoxconn.npi.dmp.api.service.model.OpenWorkOrder;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * OtOpenWorkOrder dao
 */
@Repository
public interface OtOpenWorkOrderDao extends JpaRepository<OtOpenWorkOrderEntity, OtOpenWorkOrderPk> {
  /**
   * 抓取實際消耗率最高的未結工單資訊
   *
   * @param startDate   班別日期的開始日期
   * @param endDate     班別日期的結束日期
   * @return OtOpenWorkOrderEntity列表
   */
  @Query(value = "select \"ShowDate\", \"HideDate\", \"OrganizationName\", \"WorkNumber\", \"ClassCode\", \"Status\", \"Creater\", \"AssemblyPartNumber\", \"AssemblyPartName\", \"StartDate\", \"TargetQuantity\", \"CompletedQuantity\", \"ComponentPartNumber\", \"ComponentPartName\", \"Unit\", \"QuantityPerAssembly\", \"ComponentYieldFactor\", \"RequiredQuantity\", \"QuantityIssued\", \"ClassId\", \"ProgramUpdateDate\" "
      + "from ("
      + "        select row_number() OVER (PARTITION BY \"WorkNumber\", \"ShowDate\" ORDER BY \"StartDate\",\"WorkNumber\",\"AssemblyPartNumber\",\"LossRatio\" desc) AS row_num, * "
      + "          from ("
      + "                select *, "
      + "                       ROUND((\"CompletedQuantity\" * \"QuantityPerAssembly\")::::numeric, 4) as \"QuantityUsed\","
      + "                       ROUND((\"QuantityIssued\" - \"CompletedQuantity\" * \"QuantityPerAssembly\" / \"ComponentYieldFactor\")::::numeric, 4) as \"QuantityLoss\", "
      + "                       COALESCE(ROUND(((\"QuantityIssued\" - \"CompletedQuantity\" * \"QuantityPerAssembly\" / \"ComponentYieldFactor\")/NULLIF(\"QuantityIssued\",0)*100)::::numeric, 2),100) as \"LossRatio\","
      + "                       date_part('day', current_date - \"StartDate\")+1 as \"OrderAge\""
      + "                  FROM \"log\".\"ot_open_work_order\" "
      + "                 WHERE \"QuantityIssued\" > 0 "
      + "                   AND \"ShowDate\" >= ?1 and \"ShowDate\" <= ?2 "
      + "                 ORDER BY \"ShowDate\", \"StartDate\", \"WorkNumber\",\"AssemblyPartNumber\", \"LossRatio\" desc "
      + "                ) w "
      + "     ) w2 "
      + " where row_num = 1 "
      + " ORDER BY \"ShowDate\", \"StartDate\", \"WorkNumber\",\"AssemblyPartNumber\", \"LossRatio\" desc", nativeQuery = true)
  public List<OpenWorkOrder> findByShowDateBetween(LocalDate startDate, LocalDate endDate);
}
