package com.example.xumuxin.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.personal_activity)
public class personal_activity extends Fragment {
    @ViewInject(R.id.collection_brands)
    private RecyclerView collection_r;
    @ViewInject(R.id.product_recommand)
    private RecyclerView recommand_r;
    private List<brandInfo> data_collection = new ArrayList<>();
    private List<recommand_item> data_recommand = new ArrayList<>();
    private brand_adapter adapter_collection;
    private recommand_adapter adapter_recommand;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        try {
            initDatas();
            initView(view);
        } catch (Exception e) {
            Log.e("personal activity", "wrong!", e);
        }
        return view;
    }

    void initDatas() {
        data_collection.add(new brandInfo("ss", "acura"));
        data_collection.add(new brandInfo("ss", "audi"));
        data_collection.add(new brandInfo("ss", "benz"));
        data_collection.add(new brandInfo("ss", "karas"));

        data_recommand.add(new recommand_item("car", "acura", "ss"));
        data_recommand.add(new recommand_item("food", "M", "ss"));
        data_recommand.add(new recommand_item("music", "ps", "ss"));
        data_recommand.add(new recommand_item("entertainment", "game", "ss"));
    }

    void initView(View view) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        collection_r.setLayoutManager(linearLayoutManager);
        adapter_collection = new brand_adapter(getContext(), data_collection);
        collection_r.setAdapter(adapter_collection);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recommand_r.setLayoutManager(linearLayoutManager1);
        adapter_recommand = new recommand_adapter(getContext(), data_recommand);
        recommand_r.setAdapter(adapter_recommand);
    }
}
