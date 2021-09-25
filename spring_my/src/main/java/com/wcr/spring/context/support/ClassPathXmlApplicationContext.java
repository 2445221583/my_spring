package com.wcr.spring.context.support;
/**
 * 最终实现类
 * */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String configLocations){
        this(new String[]{configLocations});
    }
    /**
     * 载入beandifinition
     * 初始化
     * */
    public ClassPathXmlApplicationContext(String[] configLocations){
        this.configLocations = configLocations;
        reflesh();
    }
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
