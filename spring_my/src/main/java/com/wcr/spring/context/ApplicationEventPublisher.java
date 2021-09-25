package com.wcr.spring.context;

public interface ApplicationEventPublisher {
    /**
     * 发布某一事件的方法
     * */
    void publishEvent(ApplicationEvent event);
}
