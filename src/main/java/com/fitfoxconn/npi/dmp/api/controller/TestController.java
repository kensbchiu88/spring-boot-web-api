package com.fitfoxconn.npi.dmp.api.controller;

import com.fitfoxconn.npi.dmp.api.model.GetFunctionIdListRs;
import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import com.fitfoxconn.npi.dmp.api.service.FunctionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    FunctionListService functionListService;
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/functionidlist")
    public List<GetFunctionIdListRs> getFunctionIdList() {
        List response = new ArrayList();
        List<OrganizeEntity> functionList = this.functionListService.getFunctionIdListByFatherId(52);

        functionList.stream().forEach( f -> {
            response.add(GetFunctionIdListRs.builder()
                    .title(f.getAlias())
                    .id(f.getId())
                    .build());
        });

        return response;
    }
}
