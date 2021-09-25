package com.wcr.spring.core.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 由resource提供访问文件的io
 * */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
