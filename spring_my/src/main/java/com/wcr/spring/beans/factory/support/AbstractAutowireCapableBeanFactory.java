package com.wcr.spring.beans.factory.support;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wcr.spring.beans.factory.PropertyValue;
import com.wcr.spring.beans.factory.PropertyValues;
import com.wcr.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.wcr.spring.beans.factory.config.BeanPostProcessor;
import com.wcr.spring.beans.factory.config.BeanReference;
import com.wcr.spring.beans.factory.config.Beandifinition;
import com.wcr.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.wcr.spring.beans.factory.xml.DisposableBean;
import com.wcr.spring.beans.factory.xml.InitializingBean;
import com.wcr.spring.beans.factory.xml.Aware;
import com.wcr.spring.beans.factory.xml.BeanClassLoaderAware;
import com.wcr.spring.beans.factory.xml.BeanFactoryAware;
import com.wcr.spring.beans.factory.xml.BeanNameAware;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 继承了abstractbeanfatory，实现createBean方法
 * */
public abstract class AbstractAutowireCapableBeanFactory extends Abstractbeanfatory implements AutowireCapableBeanFactory {

    //这里使用cglib的默认实现策略
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    public Object createBean(String beanName, Beandifinition beandifinition, Object[] args) {
        Object object=null;
        try {
            object = resolveBeforeInstantiation(beanName, beandifinition);
            //System.out.println(beanName+object);
            if (null != object) {
                return object;
            }
            object=doCreateBean(beanName,beandifinition,args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        registerDisposableBeanIfNecessary(beanName, object, beandifinition);
//        if(beandifinition.isSingleton()){
//            addSingleton(beanName,object);
//        }
        return object;
    }

    protected Object doCreateBean(String beanName, Beandifinition beandifinition, Object[] args) throws NoSuchMethodException {
        Object object=null;
        //创建未填充属性的bean
        object=createBeanInstance(beandifinition, beanName, args);

        // 处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来
        if (beandifinition.isSingleton()) {
            Object finalBean = object;
            //判断这个bean是否要执行代理，如果要的话就将代理对象存入三级缓存中
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beandifinition, finalBean));
        }

        // 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
        boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, object);
        if (!continueWithPropertyPopulation) {
            return object;
        }
        applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, object, beandifinition);
        //System.out.println(beanName+object);
        //填充属性
        applyPropertyValues(beanName, object, beandifinition);
        object=initializeBean(beanName,object,beandifinition);
        registerDisposableBeanIfNecessary(beanName, object, beandifinition);
        Object temp=object;
        if(beandifinition.isSingleton()){
            // 获取代理对象
            temp = getSingleton(beanName);
            addSingleton(beanName,temp);
        }
        return temp;
    }

    protected Object getEarlyBeanReference(String beanName, Beandifinition beandifinition, Object finalBean){
        Object exposedObject = finalBean;
        //循环遍历postprocessor,提前进行代理
        for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors()){
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                exposedObject=((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(finalBean,beanName);
                if(exposedObject==null) return exposedObject;
            }
        }
        return exposedObject;
    }

    protected Object resolveBeforeInstantiation(String beanName, Beandifinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getClazz(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }
    protected void registerDisposableBeanIfNecessary(String beanName, Object object, Beandifinition beandifinition){
        if(beandifinition.isPrototype()) return;
        if (object instanceof DisposableBean || StrUtil.isNotEmpty(beandifinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(object, beanName, beandifinition));
        }
    }
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    //调用cglib策略实现类，调用instantiate方法实现
    protected Object createBeanInstance(Beandifinition beandifinition,String beanName,Object... args) throws NoSuchMethodException {
        Constructor constructorToUse = null;
        Class clazz=beandifinition.getClazz();
        Constructor[] declaredConstructors=clazz.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beandifinition, beanName, constructorToUse, args);
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, Beandifinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != pvs) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }
    protected void applyPropertyValues(String beanName,Object object,Beandifinition beandifinition){
        PropertyValues propertyValues=beandifinition.getPropertyValues();
        PropertyValue[] pvs=propertyValues.getPropertyValues();
        for(PropertyValue pv:pvs){
            String name=pv.getName();
            Object value=pv.getValue();
            if(value instanceof BeanReference){
                BeanReference beanReference=(BeanReference)value;
                value=getBean(((BeanReference) value).getBeanName());
            }
//            String v=object;
            BeanUtil.setFieldValue(object, name, value);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result=existingBean;
        for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors()){
            Object current = beanPostProcessor.postProcessAfterInitialization(result,beanName);

            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName){
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
    private Object initializeBean(String beanName, Object bean, Beandifinition beanDefinition){
        //初始化bean的过程中让bean感知容器状态，调用对应set方法
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        Object wrappedBean=applyBeanPostProcessorsBeforeInitialization(bean,beanName);
        invokeInitMethods(beanName, wrappedBean, beanDefinition);
        wrappedBean=applyBeanPostProcessorsAfterInitialization(bean,beanName);
        return wrappedBean;
    }
    /**
     * 在初始化中判断是接口实现还是配置实现
     * */
    protected void invokeInitMethods(String beanName, Object wrappedBean, Beandifinition beanDefinition){
        Class clazz=beanDefinition.getClazz();
        if(InitializingBean.class.isAssignableFrom(clazz)){
            ((InitializingBean)wrappedBean).afterPropertiesSet();
        }else{
            String initMethodName = beanDefinition.getInitMethodName();
            if (StrUtil.isNotEmpty(initMethodName)) {
                Method initMethod = null;
                try {
                    initMethod = beanDefinition.getClazz().getMethod(initMethodName);
                    initMethod.invoke(wrappedBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
