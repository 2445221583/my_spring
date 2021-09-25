package com.wcr.spring.core.core.io;

import cn.hutool.core.lang.Assert;
import com.wcr.spring.util.ClassUtils;

import java.io.InputStream;

/**
 * 工程路径下加载
 * */
public class ClassPathResource implements Resource{
    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }
    /**
     * 类加载器进行加载
     * 提供访问文件的io
     * */
    public InputStream getInputStream() {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException(
                    this.path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
