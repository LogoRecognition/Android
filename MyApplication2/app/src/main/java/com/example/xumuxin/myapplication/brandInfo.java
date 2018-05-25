package com.example.xumuxin.myapplication;

/**
 * Created by XDDN2 on 2018/5/7.
 */

public class brandInfo {
    private String url;
    private String name;

    public brandInfo(String u, String n) {
        url = u;
        name = n;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
