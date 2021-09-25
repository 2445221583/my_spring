package com.wcr.spring.beans.factory.support;



import com.wcr.spring.beans.factory.xml.ConfigurableListableBeanFactory;
import com.wcr.spring.beans.factory.config.Beandifinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 最终实现类，包含前面的模板类的功能
 * 实现了beandefinitionregistry接口，实现了get和注册侧的功能
 * 这时的ioc容器在原来的基础上有了许多的拓展
 * */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private Map<String, Beandifinition> map=new ConcurrentHashMap<String, Beandifinition>();
    protected Beandifinition getBeandifinition(String beanName) {
        return map.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return map.containsKey(beanName);
    }

    public void registryBeandifinition(String beanName, Beandifinition beandifinition) {
        map.put(beanName,beandifinition);
    }

    public Beandifinition getBeanDefinition(String beanName) {
        return map.get(beanName);
    }

    public void preInstantiateSingletons() {
        //直接调用getBean将单例加入到单例池中
//        map.keySet().forEach(this::getBean);
//        for(String key:map.keySet()){
//            getBean(key);
//        }
        map.keySet().forEach(this::getBean);
    }



    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>();

        map.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getClazz();
            //System.out.println(beanClass);
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    public String[] getBeanDefinitionNames() {
        return map.keySet().toArray(new String[0]);
    }


    @Override
    public <T> T getBean(Class<T> requiredType) {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, Beandifinition> entry : map.entrySet()) {
            Class beanClass = entry.getValue().getClazz();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new RuntimeException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}


