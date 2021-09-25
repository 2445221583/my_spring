package com.wcr.spring.context.event;

import com.wcr.spring.context.ApplicationEvent;

/**
 * 容器关闭的事件
 * */
public class ContextClosedEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }


}
