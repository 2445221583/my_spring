package com.wcr.spring.advice;

import com.wcr.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AdviceTest implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("拦截了"+method.getName());
    }
}
