package com.wcr.spring.beans.factory.xml;

import com.wcr.spring.beans.factory.xml.Aware;
import com.wcr.spring.context.ApplicationContext;

public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext);
}
