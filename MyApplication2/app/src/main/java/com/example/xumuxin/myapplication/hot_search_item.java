package com.example.xumuxin.myapplication;

/**
 * Created by XDDN2 on 2018/5/8.
 */

public class hot_search_item {
    private String url;
    private String name;
    private String search_time;

    public hot_search_item(String n, String s, String u) {
        name = n;
        search_time = s;
        url = u;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearch_time() {
        return search_time;
    }

    public void setSearch_time(String search_time) {
        this.search_time = search_time;
    }
}
