package com.wcr.spring.aop.framework.autoproxy;

import com.wcr.spring.aop.AdvisedSupport;
import com.wcr.spring.aop.ClassFilter;
import com.wcr.spring.aop.Pointcut;
import com.wcr.spring.aop.TargetSource;
import com.wcr.spring.aop.aspecj.AspectJExpressionPointcutAdvisor;
import com.wcr.spring.aop.Advisor;
import com.wcr.spring.aop.framework.ProxyFactory;
import com.wcr.spring.beans.factory.xml.Beanfactory;
import com.wcr.spring.beans.factory.PropertyValues;
import com.wcr.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.wcr.spring.beans.factory.xml.BeanFactoryAware;
import com.wcr.spring.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class postProcessBeforeInstantiation implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {
    private DefaultListableBeanFactory beanFactory;
    //保存代理对象
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<Object>());

    @Override
    public void setBeanFactory(Beanfactory beanFactory){
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }



    /**
     * 判断当前构造的对象是否是advisor对象，如果是就直接退出
     * */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName){
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName){
        if(!earlyProxyReferences.contains(beanName)){
            return wrapIfNecessary(bean,beanName);
        }
        return bean;
    }

    public Object wrapIfNecessary(Object bean,String beanName) {
        if (isInfrastructureClass(bean.getClass())) return bean;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }


    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        return pvs;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
//        if (isInfrastructureClass(beanClass)) return null;
//
//        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
//
//        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
//            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
//            if (!classFilter.matches(beanClass)) continue;
//
//            AdvisedSupport advisedSupport = new AdvisedSupport();
//
//            TargetSource targetSource = null;
//            try {
//                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            advisedSupport.setTargetSource(targetSource);
//            //System.out.println((MethodInterceptor) advisor.getAdvice()+"000");
//            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
//            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//            advisedSupport.setProxyTargetClass(false);
//
//            return new ProxyFactory(advisedSupport).getProxy();
//
//        }

        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        return true;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }


}
