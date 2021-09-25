package com.wcr.spring.context.support;

import com.wcr.spring.beans.factory.xml.ConfigurableListableBeanFactory;
import com.wcr.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.wcr.spring.beans.factory.config.BeanPostProcessor;
import com.wcr.spring.context.ApplicationEvent;
import com.wcr.spring.context.ApplicationListener;
import com.wcr.spring.context.ConfigurableApplicationContext;
import com.wcr.spring.context.event.*;
import com.wcr.spring.core.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 对configrabaleapplicationcontext进行拓展
 * */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    public void reflesh() {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));



        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 9. 发布容器刷新完成事件
        finishRefresh();
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> map=beanFactory.getBeansOfType(BeanPostProcessor.class);
        //System.out.println(map);
        for(BeanPostProcessor beanPostProcessor:map.values()){
            //将后置处理器进行注册
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }
    //beanfatory的后置处理器
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> map=beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        //System.out.println(map);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor:map.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract void refreshBeanFactory();

    protected abstract ConfigurableListableBeanFactory getBeanFactory();


    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
    //对接口方法进行实现
    //在工厂中进行了实现，所以我们可以直接调用
    public Object getBean(String beanName, Object... args) {
        return getBeanFactory().getBean(beanName,args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType){
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType){
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);

    }
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }


}
