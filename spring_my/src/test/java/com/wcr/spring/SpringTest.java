package com.wcr.spring;

import com.wcr.spring.Service.Aservice;
import com.wcr.spring.Service.IAdminService;
import com.wcr.spring.Service.UserService;

import com.wcr.spring.context.support.ClassPathXmlApplicationContext;
import com.wcr.spring.dao.UserDao;
import org.junit.Test;

public class SpringTest {
    /**
     * 测试xml注入和注解注入
     * */
    @Test
    public void testAuto(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext=new ClassPathXmlApplicationContext("classpath:spring_my.xml");
        UserDao userDao=(UserDao) classPathXmlApplicationContext.getBean("userDao");
        System.out.println(userDao);
        UserService userService=(UserService)classPathXmlApplicationContext.getBean("userService");
        System.out.println(userService);
        userService.select();
    }
    /**
     * 测试aop
     * */
    @Test
    public void testAop(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext=new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IAdminService iAdminService=(IAdminService)classPathXmlApplicationContext.getBean("adminService");
        iAdminService.queryUserInfo();
    }
    /**
     * 测试循环依赖
     * */
    @Test
    public void testDepend(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext=new ClassPathXmlApplicationContext("classpath:spring_my.xml");
        Aservice aservice=(Aservice)classPathXmlApplicationContext.getBean("aservice");
        System.out.println(aservice);
    }
}
