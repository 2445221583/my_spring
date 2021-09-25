package com.wcr.spring.aop;

import com.wcr.spring.aop.BeforeAdvice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 方法执行前的aop
     * */
    void before(Method method, Object[] args, Object target);
}
