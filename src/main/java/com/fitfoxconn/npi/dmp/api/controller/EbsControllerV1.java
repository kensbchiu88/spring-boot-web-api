package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetOpenWorkOrderRs;
import com.fitfoxconn.npi.dmp.api.model.GetOpenWorkOrderRs.OpenWorkOrder;
import com.fitfoxconn.npi.dmp.api.model.GetProductTransactionsRs;
import com.fitfoxconn.npi.dmp.api.model.GetProductTransactionsRs.ProductTransaction;
import com.fitfoxconn.npi.dmp.api.service.OpenWorkOrderService;
import com.fitfoxconn.npi.dmp.api.service.OpenWorkOrderService.GetOpenWorkOrderByDateOutput;
import com.fitfoxconn.npi.dmp.api.service.ProductTransactionService;
import com.fitfoxconn.npi.dmp.api.service.ProductTransactionService.getProductTransactionOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * EBS相關api controller
 */
@Tag(name = "EbsControllerV1", description = "EBS相關API")
@RestController
@RequestMapping("/api/v1/ebs")
public class EbsControllerV1 {

  @Autowired
  private OpenWorkOrderService openWorkOrderService;

  @Autowired
  private ProductTransactionService productTransactionService;

  @GetMapping(value = "/open-work-orders")
  @Operation(summary = "抓取實際消耗率最高的未結工單資訊", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "startDate", description = "查詢日期起", schema = @Schema(example = "2023-05-11"))
  @Parameter(name = "endDate", description = "查詢日期訖", schema = @Schema(example = "2023-05-12"))
  public List<GetOpenWorkOrderRs> getOpenWorkOrder(@RequestParam String startDate,
      @RequestParam String endDate) {
    List<GetOpenWorkOrderByDateOutput> serviceOutput = this.openWorkOrderService.getOpenWorkOrderByDate(
        LocalDate.parse(startDate), LocalDate.parse(endDate));

    return transformOpenWorkOrders(serviceOutput);
  }

  @GetMapping(value = "/product-transactions")
  @Operation(summary = "抓取產品庫存明細", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "partNumber", description = "料號", schema = @Schema(example = "QT4013Y-D0332-7H"))
  @Parameter(name = "startDate", description = "查詢日期起", schema = @Schema(example = "2023-05-11"))
  @Parameter(name = "endDate", description = "查詢日期訖", schema = @Schema(example = "2023-05-12"))
  public List<GetProductTransactionsRs> getProductTransaction(@RequestParam String partNumber,
      @RequestParam String startDate,
      @RequestParam String endDate) {
    List<getProductTransactionOutput> serviceOutput = this.productTransactionService.getProductTransaction(
        partNumber, LocalDate.parse(startDate), LocalDate.parse(endDate));

    return transformProductTransactions(serviceOutput);
  }

  /** 將service output的格式轉換成 response的格式 */
  private List<GetOpenWorkOrderRs> transformOpenWorkOrders(List<GetOpenWorkOrderByDateOutput> input) {
    Map<LocalDate, List<OpenWorkOrder>> tempMap = new TreeMap<>();
    input.forEach(i -> {
      List<OpenWorkOrder> tempList;
      if (tempMap.containsKey(i.getShowDate())) {
        tempList = tempMap.get(i.getShowDate());
      } else {
        tempList = new ArrayList<>();
      }
      OpenWorkOrder tempOpenWorkOrder = new OpenWorkOrder();
      BeanUtils.copyProperties(i, tempOpenWorkOrder);
      tempList.add(tempOpenWorkOrder);
      tempMap.put(i.getShowDate(), tempList);
    });

    List<GetOpenWorkOrderRs> result = new ArrayList<>();

    for (LocalDate key : tempMap.keySet()) {
      result.add(
          GetOpenWorkOrderRs.builder().showDate(key).data(tempMap.get(key)).build());
    }

    return result;
  }

  /** 將service output的格式轉換成 response的格式 */
  private List<GetProductTransactionsRs> transformProductTransactions(List<getProductTransactionOutput> input) {
    List<GetProductTransactionsRs> result = new ArrayList<>();
    Map<LocalDate, List<ProductTransaction>> tempMap = new TreeMap<>();
    input.forEach(i -> {
      List<ProductTransaction> tempList;
      if (tempMap.containsKey(i.getSearchDate().toLocalDate())) {
        tempList = tempMap.get(i.getSearchDate().toLocalDate());
      } else {
        tempList = new ArrayList<>();
      }
      ProductTransaction tempProductTransaction = new ProductTransaction();
      BeanUtils.copyProperties(i, tempProductTransaction);
      tempList.add(tempProductTransaction);
      tempMap.put(i.getSearchDate().toLocalDate(), tempList);
    });

    for (LocalDate key : tempMap.keySet()) {
      result.add(
          GetProductTransactionsRs.builder().searchDate(key).data(tempMap.get(key)).build());
    }

    return result;
  }
}
