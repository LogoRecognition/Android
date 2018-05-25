package com.example.xumuxin.myapplication;

/**
 * Created by XDDN2 on 2018/5/7.
 */

public class product_item {
    private String url;
    private String name;
    private String introduce;

    public product_item(String n, String d, String u) {
        name = n;
        url = u;
        introduce = d;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
