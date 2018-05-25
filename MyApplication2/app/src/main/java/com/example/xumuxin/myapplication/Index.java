package com.example.xumuxin.myapplication;

import java.util.List;

/**
 * Created by XDDN2 on 2018/5/16.
 */

public class Index {
    private List<List<String>> searching_indexes;
    public Index(List<List<String>> d) {
        searching_indexes = d;
    }

    public List<List<String>> getData() {
        return searching_indexes;
    }

    public int[] getPos(int p) {
        if (p >= searching_indexes.size())
            return null;
        int[] res = new int[searching_indexes.get(p).size()];
        for (int i = 0; i < res.length; ++i) {
            res[i] = Integer.parseInt(searching_indexes.get(p).get(i));
        }
        return res;
    }
}
