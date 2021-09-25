package com.wcr.spring.context.event;

import com.wcr.spring.context.ApplicationContext;
import com.wcr.spring.context.ApplicationEvent;

/**
 * 容器事件的抽象类
 * */
public abstract class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
