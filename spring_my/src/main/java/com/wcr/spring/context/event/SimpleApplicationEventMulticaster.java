package com.wcr.spring.context.event;

import com.wcr.spring.beans.factory.xml.Beanfactory;
import com.wcr.spring.context.ApplicationEvent;
import com.wcr.spring.context.ApplicationListener;

/**
 * 被观察者的最终实现类
 * */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(Beanfactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
