package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.repository.ods.OrganizeDao;
import com.fitfoxconn.npi.dmp.api.entity.ods.OrganizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionListService {
    @Autowired
    private OrganizeDao organizeDao;

    public List<OrganizeEntity> getFunctionIdListByFatherId(int fatherId) {
        return this.organizeDao.findByFatherId(fatherId);
    }


    /*
    // API程式不複雜，output不需要DTO這層，可加快開發速度
    public static class GetFunctionOutput {

    }
    */
}
