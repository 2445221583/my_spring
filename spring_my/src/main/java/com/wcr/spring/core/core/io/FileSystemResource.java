package com.wcr.spring.core.core.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 文件路径访问
 * */
public class FileSystemResource implements Resource{
    private String path;
    private File file;

    public FileSystemResource(String path) {
        this.path = path;
        file=new File(path);
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path=file.getPath();
    }


    public InputStream getInputStream() {
//        File file=new File(path);
        try {
            FileInputStream inputStream=new FileInputStream(path);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
