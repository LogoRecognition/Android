package com.example.xumuxin.myapplication;

import java.util.List;

/**
 * Created by XDDN2 on 2018/5/16.
 */

public class Brand_search_items {
    private List<String> brand_names;
    private List<String> brand_logos;
    private List<String> url;
    public Brand_search_items(List<String> names, List<String> logos) {
        brand_names = names;
        brand_logos = logos;
    }

    public List<String> getBrand_names() {
        return brand_names;
    }

    private void saveImageInCache() {
        return;
    }

    public List<String> getUrl() {
        return url;
    }
}
