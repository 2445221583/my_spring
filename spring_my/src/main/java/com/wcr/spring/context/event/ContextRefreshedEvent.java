package com.wcr.spring.context.event;

import com.wcr.spring.context.ApplicationEvent;

/**
 * 容器刷新的事件
 * */
public class ContextRefreshedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
