package com.wcr.spring.context.support;

import com.wcr.spring.beans.factory.support.DefaultListableBeanFactory;
import com.wcr.spring.beans.factory.support.XmlBeanDefinitionReader;

/**
 *
 * */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();

}
