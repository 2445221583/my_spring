package com.wcr.spring.aop.framework;

import com.wcr.spring.aop.AdvisedSupport;

public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }
        //System.out.println(advisedSupport.toString());
        return new JdkDynamicAopProxy(advisedSupport);
    }

}
