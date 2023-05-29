package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetOpenWorkOrderRs;
import com.fitfoxconn.npi.dmp.api.model.GetOpenWorkOrderRs.OpenWorkOrder;
import com.fitfoxconn.npi.dmp.api.service.OpenWorkOrderService;
import com.fitfoxconn.npi.dmp.api.service.OpenWorkOrderService.GetOpenWorkOrderByDateOutput;
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
  OpenWorkOrderService openWorkOrderService;

  @GetMapping(value = "/open-work-order")
  @Operation(summary = "抓取實際消耗率最高的未結工單資訊", security = @SecurityRequirement(name = "bearerAuth"))
  @Parameter(name = "startDate", description = "查詢日期起", schema = @Schema(example = "2023-05-11"))
  @Parameter(name = "endDate", description = "查詢日期訖", schema = @Schema(example = "2023-05-12"))
  public List<GetOpenWorkOrderRs> getOpenWorkOrder(@RequestParam String startDate,
      @RequestParam String endDate) {
    List<GetOpenWorkOrderByDateOutput> serviceOutput = this.openWorkOrderService.getOpenWorkOrderByDate(
        LocalDate.parse(startDate), LocalDate.parse(endDate));

    //轉換成response格式
    Map<LocalDate, List<OpenWorkOrder>> responseTempMap = new TreeMap<>();
    serviceOutput.forEach(i -> {
      List<OpenWorkOrder> tempList;
      if(responseTempMap.containsKey(i.getShowDate())){
        tempList = responseTempMap.get(i.getShowDate());
      } else {
        tempList = new ArrayList<>();
      }
      OpenWorkOrder tempOpenWorkOrder = new OpenWorkOrder();
      BeanUtils.copyProperties(i, tempOpenWorkOrder);
      tempList.add(tempOpenWorkOrder);
      responseTempMap.put(i.getShowDate(), tempList);
    });

    List<GetOpenWorkOrderRs> response = new ArrayList<>();

    for (LocalDate key : responseTempMap.keySet()) {
      response.add(GetOpenWorkOrderRs.builder().showDate(key).data(responseTempMap.get(key)).build());
    }

    return response;
  }
}
