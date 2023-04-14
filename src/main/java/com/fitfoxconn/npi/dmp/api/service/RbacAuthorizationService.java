package com.fitfoxconn.npi.dmp.api.service;

import com.fitfoxconn.npi.dmp.api.repository.api.RbacResourceDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RbacAuthorizationService {

    @Autowired
    private RbacResourceDao rbacResourceDao;

    /** 檢驗user是否有呼叫resource的權限 */
    public boolean hasPermission(String username, String resource) {
        log.info("##########check permission: username:{}, resource:{}", username, resource);
        int count = this.rbacResourceDao.CountActiveResourceByUsernameAndResourceName(username, resource);
        if(count > 0)
            return true;
        return false;
    }
}
