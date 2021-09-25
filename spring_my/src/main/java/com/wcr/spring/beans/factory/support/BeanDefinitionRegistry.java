package com.wcr.spring.beans.factory.support;


import com.wcr.spring.beans.factory.config.Beandifinition;

public interface BeanDefinitionRegistry {
    boolean containsBeanDefinition(String beanName);

    public void registryBeandifinition(String beanName, Beandifinition beandifinition);
}
