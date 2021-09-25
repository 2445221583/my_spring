package com.wcr.spring.beans.factory.support;

import com.wcr.spring.beans.factory.config.SingletonBeanRegistry;
import com.wcr.spring.beans.factory.xml.DisposableBean;
import com.wcr.spring.beans.factory.xml.ObjectFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实习SingleBeanregistry接口
 * 实现get，add方法
 * */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected static final Object NULL_OBJECT = new Object();
//    private Map<String,Object> singletonMap=new HashMap<String, Object>();
//    private Map<String, DisposableBean> DisposMap=new HashMap<>();
//    public Object getSingleton(String beanName) {
//        return singletonMap.get(beanName);
//    }
//    public void addSingleton(String beanName, Object singletonObject) {
//        singletonMap.put(beanName, singletonObject);
//    }
//    //加入到定义的销毁bean中
//    protected void registerDisposableBean(String beanName,DisposableBean bean){
//        DisposMap.put(beanName,bean);
//    }
//    //close的时候表里list,之后从后执行destry方法
//    public void destroySingletons() {
//        Set<String> keySet = this.DisposMap.keySet();
//        Object[] disposableBeanNames = keySet.toArray();
//
//        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
//            //从后往前遍历执行销毁的方法
//            Object beanName = disposableBeanNames[i];
//            DisposableBean disposableBean = DisposMap.remove(beanName);
//            try {
//                disposableBean.destroy();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    //一级缓存，存放普通对象
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    //提前暴露对象，将对象存放于二级缓存，二级缓存中存放的是半成品对象
    protected final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>();
    //三级缓存存放代理对象或者工厂
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        //如果一级缓存取不到
        if(singletonObject==null){
            //去二级缓存找半成品对象
            singletonObject=earlySingletonObjects.get(beanName);
            if(singletonObject==null){
                //二级缓存中找不到，就去三级缓存中找
                ObjectFactory<?> objectFactorys = singletonFactories.get(beanName);
                if(objectFactorys!=null){
                    singletonObject=objectFactorys.getObject();
                    //将对象放入二级缓存，并且清除对应的三级缓存
                    earlySingletonObjects.put(beanName,singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }
    //一级缓存中注册bean
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }
    //二级缓存，三级缓存只能有一个存在
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }
    //将容器关闭导致的消亡对象加入到list中
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                //执行bean的销毁方法
                disposableBean.destroy();
            } catch (Exception e) {
                throw new RuntimeException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
//        System.out.println(singletonObject);
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }
}
