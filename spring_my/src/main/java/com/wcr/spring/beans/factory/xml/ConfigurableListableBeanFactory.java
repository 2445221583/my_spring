package com.wcr.spring.beans.factory.xml;

import com.wcr.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.wcr.spring.beans.factory.config.BeanPostProcessor;
import com.wcr.spring.beans.factory.config.Beandifinition;
import com.wcr.spring.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    Beandifinition getBeanDefinition(String beanName);
    //将非懒加载单例加到单例池中
    void preInstantiateSingletons();
    //在bean初始化之前将beanpost注册到容器中
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
