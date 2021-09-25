package com.wcr.spring.Service;

import com.wcr.spring.beans.factory.annotation.Autowired;
import com.wcr.spring.beans.factory.annotation.Component;
import com.wcr.spring.dao.UserDao;
@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public void select(){
        userDao.select();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
