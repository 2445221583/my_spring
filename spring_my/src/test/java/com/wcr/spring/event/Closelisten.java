package com.wcr.spring.event;

import com.wcr.spring.context.ApplicationListener;
import com.wcr.spring.context.event.ContextClosedEvent;

public class Closelisten implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("执行了销毁");
    }
}
