package com.wcr.spring.beans.factory.config;

public interface AutowireCapableBeanFactory {
    //执行befor
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName);
    //执行after方法，会在list中遍历进行调用
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName);
}
