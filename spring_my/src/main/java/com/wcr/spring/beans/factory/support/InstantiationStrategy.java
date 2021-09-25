package com.wcr.spring.beans.factory.support;

import com.wcr.spring.beans.factory.config.Beandifinition;

import java.lang.reflect.Constructor;

/**
 * 实例化bean的策略接口
 * 有两种实现策略
 * 一种是jdk实现
 * 一种是cglib
 * 采用了策略模式
 * */
public interface InstantiationStrategy {
    Object instantiate(Beandifinition beanDefinition, String beanName, Constructor ctor, Object[] args);
}
