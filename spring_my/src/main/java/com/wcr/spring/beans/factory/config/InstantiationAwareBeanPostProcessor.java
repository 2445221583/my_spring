package com.wcr.spring.beans.factory.config;

import com.wcr.spring.beans.factory.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName);
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName);
    boolean postProcessAfterInstantiation(Object bean, String beanName);
    public Object getEarlyBeanReference(Object bean, String beanName);
}
