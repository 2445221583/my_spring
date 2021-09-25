package com.wcr.spring.beans.factory.config;

import com.wcr.spring.beans.factory.PropertyValues;
import com.wcr.spring.beans.factory.config.ConfigurableBeanFactory;

/**
 * beandifinition中包含着依赖信息
 * */
public class Beandifinition {
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private Class clazz;

    private PropertyValues propertyValues;      //以来列表，在createbean时候遍历列表进行注入

    private String initMethodName;

    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public Beandifinition(Class clazz) {
        this.clazz = clazz;
        this.propertyValues=new PropertyValues();
    }

    public Beandifinition(Class clazz, PropertyValues propertyValues) {
        this.clazz = clazz;
        this.propertyValues = propertyValues!=null?propertyValues:new PropertyValues();
    }

    public Beandifinition(Class clazz, PropertyValues propertyValues, String initMethodName, String destroyMethodName) {
        this.clazz = clazz;
        this.propertyValues = propertyValues;
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getObject() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void setScope(String scope) {
        this.scope = scope;
        //判断单例多例
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }
}
