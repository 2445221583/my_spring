package com.wcr.spring.beans.factory.xml;

public interface BeanFactoryAware extends Aware {
    void setBeanFactory(Beanfactory beanFactory);
}
