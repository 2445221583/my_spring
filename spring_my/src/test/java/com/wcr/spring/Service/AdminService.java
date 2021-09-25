package com.wcr.spring.Service;

import com.wcr.spring.Service.IAdminService;
import com.wcr.spring.beans.factory.annotation.Autowired;
import com.wcr.spring.beans.factory.annotation.Component;
import com.wcr.spring.beans.factory.annotation.Qualifier;
import com.wcr.spring.beans.factory.annotation.Value;
import com.wcr.spring.dao.AdminDao;

@Component("adminService")
public class AdminService implements IAdminService {
    @Value("${token}")
    private String token;

    @Autowired
    @Qualifier("adminDao")
    private AdminDao adminDao;

    public void queryUserInfo() {
        System.out.println("执行了查询");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }
}
