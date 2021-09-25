package com.wcr.spring.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.wcr.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.wcr.spring.beans.factory.annotation.Component;
import com.wcr.spring.beans.factory.config.Beandifinition;
import com.wcr.spring.beans.factory.support.BeanDefinitionRegistry;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<Beandifinition> candidates = findCandidateComponents(basePackage);
            for (Beandifinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registryBeandifinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        //扫描完compant后，将注解的后置处理器加入到map中
        registry.registryBeandifinition("com.wcr.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor",new Beandifinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(Beandifinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazz();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }

    private String determineBeanName(Beandifinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getClazz();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
