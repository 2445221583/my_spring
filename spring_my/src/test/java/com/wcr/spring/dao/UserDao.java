package com.wcr.spring.dao;

import com.wcr.spring.beans.factory.annotation.Component;

@Component
public class UserDao {
    public void select(){
        System.out.println("执行了查询");
    }
}
