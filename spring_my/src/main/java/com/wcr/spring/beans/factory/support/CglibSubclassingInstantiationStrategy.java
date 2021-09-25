package com.wcr.spring.beans.factory.support;

import com.wcr.spring.beans.factory.config.Beandifinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * cglib的策略实现类
 * */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{
    public Object instantiate(Beandifinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(beanDefinition.getClazz());
        enhancer.setCallback(
                new NoOp() {
                    @Override
                    public int hashCode() {
                        return super.hashCode();
                    }
                }
        );
        if(ctor==null) return enhancer.create();
        return enhancer.create(ctor.getParameterTypes(),args);
    }
}
