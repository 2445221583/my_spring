package com.wcr.spring.event;

import com.wcr.spring.context.ApplicationListener;

import java.util.Date;

public class Mylisten implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
    }
}
