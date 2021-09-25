package com.wcr.spring.aop;

public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
