package com.wcr.spring.context;

import java.util.EventListener;

/**
 * 监听器接口，并且定义是那种事件的监听器
 * */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);

}
