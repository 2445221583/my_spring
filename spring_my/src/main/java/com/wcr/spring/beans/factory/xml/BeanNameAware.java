package com.wcr.spring.beans.factory.xml;

public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
