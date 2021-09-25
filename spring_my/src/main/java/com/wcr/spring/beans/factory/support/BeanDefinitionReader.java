package com.wcr.spring.beans.factory.support;

import com.wcr.spring.core.core.io.Resource;
import com.wcr.spring.core.core.io.ResourceLoader;

/**
 * 该接口定义注册beandifinition的方法和获取resouceloader的方法
 * 再次采用了模板模式
 * */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();
    /**
     * 加载beandefinition
     * */
    void loadBeanDefinitions(Resource resource);

    void loadBeanDefinitions(Resource... resources);

    void loadBeanDefinitions(String location);

    void loadBeanDefinitions(String... locations);

}
