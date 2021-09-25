package com.wcr.spring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.wcr.spring.beans.factory.annotation.Component;
import com.wcr.spring.beans.factory.config.Beandifinition;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {
    public Set<Beandifinition> findCandidateComponents(String basePackage) {
        Set<Beandifinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new Beandifinition(clazz));
        }
        return candidates;
    }
}
