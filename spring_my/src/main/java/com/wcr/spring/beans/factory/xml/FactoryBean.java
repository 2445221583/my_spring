package com.wcr.spring.beans.factory.xml;
/**
 * 类似于生产bean的工厂
 * */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();

}