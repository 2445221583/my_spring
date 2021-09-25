package com.wcr.spring.core.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource{
    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url,"URL must not be null");
        this.url = url;
    }

    @Override
    public InputStream getInputStream(){
        URLConnection con = null;
        try {
            con = this.url.openConnection();
            try {
                return con.getInputStream();
            }
            catch (IOException ex){
                if (con instanceof HttpURLConnection){
                    ((HttpURLConnection) con).disconnect();
                }
                throw ex;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
}
