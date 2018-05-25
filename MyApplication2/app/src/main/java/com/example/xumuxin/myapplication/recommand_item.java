package com.example.xumuxin.myapplication;

/**
 * Created by XDDN2 on 2018/5/8.
 */

public class recommand_item {
    private String category, name, url;

    public recommand_item(String c, String n, String u) {
        category = c;
        name = n;
        url = u;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
