package com.wcr.spring.aop;

public interface Pointcut {
    //获取匹配类型的匹配器
    ClassFilter getClassFilter();
    //匹配方法
    MethodMatcher getMethodMatcher();
}
