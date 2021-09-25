package com.wcr.spring.aop;

import com.wcr.spring.aop.Advisor;
import com.wcr.spring.aop.Pointcut;

public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
