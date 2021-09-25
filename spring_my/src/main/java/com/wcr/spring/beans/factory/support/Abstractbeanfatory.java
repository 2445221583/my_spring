package com.wcr.spring.beans.factory.support;


import com.wcr.spring.beans.factory.xml.Beanfactory;
import com.wcr.spring.beans.factory.config.BeanPostProcessor;
import com.wcr.spring.beans.factory.config.Beandifinition;
import com.wcr.spring.beans.factory.config.ConfigurableBeanFactory;
import com.wcr.spring.beans.factory.xml.FactoryBean;
import com.wcr.spring.util.ClassUtils;
import com.wcr.spring.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 采用模板模式用abstractBeanfatory来实现beanfactory同时实现getbean方法
 * 这时执行getbean方法需要用到一些方法
 * 从单例池中获取对象，或者创建对象
 * 使用模板方法，不需要去关心方法如何去实现，只需要关心逻辑
 * 方法的实现由子类去实现，从而实现解耦
 * abstractbeanfatory从SingletonBeanRegistry中继承到getSingletonBean的方法
 * createBean由子类实现
 * */
public abstract class Abstractbeanfatory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory,Beanfactory {
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    //提供getBean方法
    public Object getBean(String beanName, Object... args) {
//        if(getSingleton(beanName)!=null)
//            return getSingleton(beanName);
//        Beandifinition beandifinition=getBeandifinition(beanName);
//        return createBean(beanName,beandifinition,args);
        return doGetBean(beanName,args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType){
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        Beandifinition beanDefinition = getBeandifinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract Beandifinition getBeandifinition(String beanName);
    protected abstract Object createBean(String beanName,Beandifinition beandifinition,Object[] args);

    //将后置处理器加入到list中
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
        System.out.println(beanPostProcessors);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        //System.out.println(beanPostProcessors);
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader(){
        return this.beanClassLoader;
    }
}
