package com.wcr.spring.context;

public interface ConfigurableApplicationContext extends ApplicationContext{
    public void reflesh();

    void registerShutdownHook();

    void close();

}
