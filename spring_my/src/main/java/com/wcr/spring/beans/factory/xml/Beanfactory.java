package com.wcr.spring.beans.factory.xml;
/**
 * 设计beanfatory的接口，提供beanfatory最初始的功能
 * 之后使用模板模式来拓展功能
 * 最终实现类中能包含所有模板的功能
 * 同时也能将逻辑与方法实现分离，实现解耦
 * 最终实现类只需要关注逻辑的实现而不需要去关注方法如何实现
 * */
public interface Beanfactory {

    Object getBean(String beanName, Object... args); //使用有参构造需要传递参数列表
    //在生成bean的时候会根据参数列表来选择构造器构造

    <T> T getBean(Class<T> requiredType);

    <T> T getBean(String name, Class<T> requiredType);
}
