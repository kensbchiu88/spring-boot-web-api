package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetFunctionIdListRs;
import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import com.fitfoxconn.npi.dmp.api.service.FunctionListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    FunctionListService functionListService;

    @GetMapping("/hello")
    @Operation(summary = "測試", security = @SecurityRequirement(name = "bearerAuth"))
    public String hello() throws Exception {
        throw new Exception("test exception handler");
//        return "Hello";
    }

    @GetMapping("/functionidlist")
    @Operation(summary = "取得function id列表", security = @SecurityRequirement(name = "bearerAuth"))
    public List<GetFunctionIdListRs> getFunctionIdList(@RequestParam("father_id") int fatherId) {
        List response = new ArrayList();
        List<OrganizeEntity> functionList = this.functionListService.getFunctionIdListByFatherId(fatherId);

        functionList.stream().forEach( f -> {
            response.add(GetFunctionIdListRs.builder()
                    .title(f.getAlias())
                    .id(f.getId())
                    .build());
        });

        return response;
    }

    @GetMapping("/huyuefunctionidlist")
    @Operation(summary = "取得虎躍廠的function id列表", security = @SecurityRequirement(name = "bearerAuth"))
    public List<GetFunctionIdListRs> getFunctionIdListInHuYue() {
        List response = new ArrayList();
        List<OrganizeEntity> functionList = this.functionListService.getFunctionIdListByFatherId(52);

        functionList.stream().forEach( f -> {
            response.add(GetFunctionIdListRs.builder()
                    .title(f.getAlias())
                    .id(f.getId())
                    .build());
        });

        System.out.println("----Response----" + response);
        return response;
    }
}
