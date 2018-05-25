package com.example.xumuxin.myapplication;

import java.util.List;

/**
 * Created by XDDN2 on 2018/5/16.
 */

public class Search_champion_item {
    private List<String> brand_names;
    private List<String> logos;
    private List<String> searching_counts;
    private List<String> categories;
    private List<String> url;

    public Search_champion_item(List<String> names, List<String> counts, List<String> c, List<String> l) {
        brand_names = names;
        searching_counts = counts;
        categories = c;
        logos = l;
    }

    public List<String> getBrand_names() {
        return brand_names;
    }

    public List<String> getCategories() {
        return categories;
    }


    public List<String> getSearching_counts() {
        return searching_counts;
    }

    private void saveImageInCache() {
        return;
    }

    public List<String> getUrl() {
        return url;
    }
}
