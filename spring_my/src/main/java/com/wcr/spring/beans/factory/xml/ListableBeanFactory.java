package com.wcr.spring.beans.factory.xml;

import com.wcr.spring.beans.factory.xml.Beanfactory;

import java.util.Map;

/**
 * 定义listablebeanfatory
 * */
public interface ListableBeanFactory extends Beanfactory {
    //根据类型返回bean对象
    <T> Map<String, T> getBeansOfType(Class<T> type);

    //返回注册表中bean名称
    String[] getBeanDefinitionNames();
}
