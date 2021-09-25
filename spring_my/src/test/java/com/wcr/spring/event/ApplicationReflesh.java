package com.wcr.spring.event;

import com.wcr.spring.context.ApplicationListener;
import com.wcr.spring.context.event.ContextRefreshedEvent;

import java.util.Date;

public class ApplicationReflesh implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date()+"执行了容器初始化");
//        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }


//    @Override
//    public void onApplicationEvent(MyEvent event) {
//        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
//        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
//    }
}
