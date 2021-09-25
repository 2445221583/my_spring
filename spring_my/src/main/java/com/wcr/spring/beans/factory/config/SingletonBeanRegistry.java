package com.wcr.spring.beans.factory.config;

public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
    public void destroySingletons();
    public void addSingleton(String beanName, Object singletonObject);
}
