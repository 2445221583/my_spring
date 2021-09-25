package com.wcr.spring.beans.factory.config;

import com.wcr.spring.beans.factory.xml.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}
