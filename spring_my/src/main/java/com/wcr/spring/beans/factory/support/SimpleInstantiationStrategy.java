package com.wcr.spring.beans.factory.support;

import com.wcr.spring.beans.factory.config.Beandifinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * jdk策略实现类
 * */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    public Object instantiate(Beandifinition beanDefinition, String beanName, Constructor ctor, Object[] args) {
        Class clazz=beanDefinition.getClazz();
        try {
            if(ctor!=null){
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
        } catch (NoSuchMethodException e) {
                e.printStackTrace();
        }
        return null;
    }
}
