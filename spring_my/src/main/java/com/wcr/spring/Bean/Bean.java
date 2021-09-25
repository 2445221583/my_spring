package com.wcr.spring.Bean;

public class Bean {
    private String name;
    private String password;

    public Bean(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void selectByid(){
        System.out.println("执行了查询");
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
